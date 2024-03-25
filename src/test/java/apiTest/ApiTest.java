package apiTest;

import base.BaseTest;
import entities.Position;
import entities.Positions;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CartPage;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static utils.api.ApiUtils.gettingCartId;
import static utils.properties.PropertyReader.getProperties;


public class ApiTest extends BaseTest {
    private String id;
    private final String baseCatalogApiUrl = "https://catalog.onliner.by/sdapi/cart.api/";
    Position productToAdd = new Position();


    @BeforeTest
    public void precondition() {
        open(getProperties().getProperty("baseCartApiUrl") + "api/cart.api/positions");
        id = gettingCartId();
    }

    @Test(priority = 1, description = "Verify empty cart")
    public void verifyEmptyCart() {
        open(getProperties().getProperty("url"));
        get(CartPage.class).verifyProductDoesntExistInCart();
    }

    @Test(priority = 2, description = "Add product in cart")
    public void addProductInCart() {
        productToAdd.setPosition_id("1689:1038576");
        productToAdd.setProduct_id(3136002);
        productToAdd.setProduct_key("u0820555r1694w");
        productToAdd.setQuantity(1);
        productToAdd.setShop_id(1689);

        Response response = given()
                .contentType("application/json")
                .body(productToAdd)
                .post(this.baseCatalogApiUrl + "detached-cart/" + this.id + "/add/");
        response.then().statusCode(200);
    }

    @Parameters("product")
    @Test(priority = 3, description = "Verify product in cart", dependsOnMethods = "addProductInCart")
    public void verifyProductInCart(String product) {
        open(getProperties().getProperty("cartUrl"));
        get(CartPage.class)
                .verifyCartPage()
                .verifyProductInCart(product);
    }

    @Test(priority = 4, description = "Remove product from cart", dependsOnMethods = "verifyProductInCart")
    public void removeProductFromCart() {
        Position position = new Position();
        position.setPosition_id("1689:1038576");
        position.setShop_id(1689);
        position.setProduct_id(3136002);
        Positions positions = new Positions(position);

        Response response = given()
                .contentType("application/json")
                .body(positions)
                .delete(getProperties().getProperty("baseCartApiUrl") + "cart.api/detached-cart/" + this.id);
        response.then().statusCode(204);
    }

    @Test(priority = 5, description = "Verify removed product", dependsOnMethods = "removeProductFromCart")
    public void verifyEmptyCartAfterRemoveProduct() {
        System.out.println(getProperties().getProperty("cartUrl"));
        open(getProperties().getProperty("cartUrl"));
        get(CartPage.class).verifyProductDoesntExistInCart();
    }
}
