package apiTest;

import base.BaseTest;
import entities.CreateProductToCart;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CartPage;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static utils.api.ApiUtils.gettingCartId;


public class ApiTest extends BaseTest {
    private String id;
    CreateProductToCart productToAdd = new CreateProductToCart();

    @BeforeTest
    public void precondition() {
        open("https://cart.onliner.by/sdapi/api/cart.api/positions");
        id = gettingCartId();
    }

    @Test(priority = 1, description = "Verify empty cart")
    public void verifyEmptyCart() {
        open("https://cart.onliner.by/");
        get(CartPage.class).verifyProductDoesntExistInCart();
    }

    @Test(priority = 2, description = "Add product in cart")
    public void addProductInCart() {
        productToAdd.setPosition_id("1689:1038576");
        productToAdd.setProduct_id(3136002);
        productToAdd.setProduct_key("u0820555r1694w");
        productToAdd.setQuantity(1);
        productToAdd.setShop_id(1689);

        Response response = given().contentType("application/json").body(productToAdd)
                .post("https://catalog.onliner.by/sdapi/cart.api/detached-cart/" + this.id + "/add/");
        response.then().statusCode(200);
    }

    @Parameters("product")
    @Test(priority = 3, description = "Verify product in cart", dependsOnMethods = "addProductInCart")
    public void verifyProductInCart(String product) {
        open("https://cart.onliner.by/");
        get(CartPage.class)
                .verifyCartPage()
                .verifyProductInCart(product);
    }

    @Test(priority = 4, description = "Remove product from cart", dependsOnMethods = "verifyProductInCart")
    public void removeProductFromCart() {
        Response response = given().contentType("application/json")
                .body("{\"positions\": [{\"position_id\": \"1689:1037895\", \"shop_id\": 1689, \"product_id\": 3865328}]}")
                .delete("https://cart.onliner.by/sdapi/cart.api/detached-cart/" + this.id);
        response.then().statusCode(204);
    }

    @Test(priority = 5, description = "Verify removed product", dependsOnMethods = "removeProductFromCart")
    public void verifyEmptyCartAfterRemoveProduct() {
        open("https://cart.onliner.by/");
        get(CartPage.class).verifyProductDoesntExistInCart();
    }
}
