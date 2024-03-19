package cartTest;

import base.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.*;

import static com.codeborne.selenide.Selenide.open;

public class CartTest extends BaseTest {

    @BeforeTest
    public void precondition() {
        open("https://catalog.onliner.by/tv/xiaomi/tvq255");
        get(ProductPage.class).verifyPage().closePrivacyPopup().clickOffersButton();

    }

    @Test(priority = 1, description = "Add product to cart")
    public void addProductInCart() {
        get(OffersPage.class)
                .verifyOffersPage()
                .addCheapestOfferInBasket()
                .closeSideBar()
                .clickOnCart();
    }

    @Test(priority = 2, description = "Verify amount products in cart" ,dependsOnMethods = "addProductInCart")
    public void verifyAmountProductsInCart() {
        get(CartPage.class).verifyCartPage().validateAmountProducts(1);
    }

    @Test(priority = 3, description = "Remove product from cart" ,dependsOnMethods = "verifyAmountProductsInCart")
    public void removeProductFromCart() {
        get(CartPage.class)
                .removeProductFromCart()
                .validateRemovedProduct();
    }

}
