package limitTes;

import base.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.OffersPage;
import utils.provider.DataProviderClass;

import static com.codeborne.selenide.Selenide.open;

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

}
