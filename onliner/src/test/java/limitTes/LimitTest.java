package limitTes;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.OffersPage;
import utils.provider.DataProviderClass;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static utils.api.ApiUtils.gettingCartId;

public class LimitTest extends BaseTest {
    @BeforeTest
    public void precondition() {
        open("https://catalog.onliner.by/notebook/apple/mgn63/prices");
        get(OffersPage.class)
                .verifyOffersPage()
                .closePrivacyPopup()
                .addCheapestOfferInBasket()
                .closeSideBar()
                .clickOnCart();
    }

    @Test(priority = 1, dataProvider = "Values to limit test", dataProviderClass = DataProviderClass.class)
    public void checkLimits(String text) {
        get(CartPage.class)
                .verifyCartPage()
                .inputTextInField(text)
                .clickOnPrice()
                .verifyProductValue(text);
    }

    @AfterTest
    public void removeProductFromCart(){
        open("https://cart.onliner.by/sdapi/api/cart.api/positions");
        String id = gettingCartId();
        Response response = given().contentType("application/json")
                .body("{\"positions\": [{\"position_id\": \"707:2049323001\", \"shop_id\": 707, \"product_id\": 2049323}]}")
                .delete("https://cart.onliner.by/sdapi/cart.api/detached-cart/" + id);
        response.then().statusCode(204);
    }
}
