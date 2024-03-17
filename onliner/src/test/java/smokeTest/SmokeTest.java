package smokeTest;

import base.BaseTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

public class SmokeTest extends BaseTest {
    private Integer productPrice;


    @Test(priority = 1, description = "Verify Home Page")
    public void verifyHomePage() {
        get(HomePage.class).openHomePage();
    }

    @Test(priority = 2, dependsOnMethods = "verifyHomePage", description = "Try to find Iphone in search")
    @Parameters("product")
    public void findProductInCatalog(String product) {
        get(HomePage.class).searchProductInSearchBar(product);
        get(SearchPopup.class)
                .validateSearchPopup()
                .searchProductInResults(product);
    }

    @Test(priority = 3, description = "Open product page", dependsOnMethods = "findProductInCatalog")
    @Parameters("product")
    public void openProductPage(String product) {
        String productUrl = get(SearchPopup.class).clickOnProductInResults(product).getProductUrl();
        get(ProductPage.class)
                .verifyPage()
                .closePrivacyPopup()
                .verifyProductName(product)
                .verifyPageUrl(productUrl);
    }

    @Test(priority = 4, description = "Chenege delivery place", dependsOnMethods = "openProductPage")
    @Parameters("delivery_location")
    public void changeDelivery(String delivery_location) {
        get(ProductPage.class)
                .changeDeliveryPlace()
                .changePlaceInDeliveryPopup(delivery_location)
                .verifyChangedLocation(delivery_location);
    }

    @Test(priority = 5, description = "Choose offer", dependsOnMethods = "changeDelivery")
    public void chooseOffer() {
        get(ProductPage.class).clickOffersButton();
        get(OffersPage.class)
                .verifyOffersPage()
                .addCheapestOfferInBasket()
                .closeSideBar()
                .verifyCartCounterAppear();
    }

    @Test(priority = 6, description = "Choose offer", dependsOnMethods = "chooseOffer")
    @Parameters("product")
    public void checkProductInCart(String product) {
        get(OffersPage.class).clickOnCart();
        this.productPrice = get(CartPage.class)
                .verifyCartPage()
                .verifyProductInCart(product)
                .increaseProductAmountByName(product)
                .getPrice();

        get(CartPage.class).makeOrderByProductName(product);
    }

    @Test(priority = 7, description = "Place an order", dependsOnMethods = "checkProductInCart")
    @Parameters({"product", "delivery_location", "street", "building", "entrance", "stair", "apartment", "name", "surname", "mail"})
    public void placingOrder(String product, String delivery_location, String street, String building, String entrance, String stair,
                             String apartment, String name, String surname, String mail) {
        get(PlacingOrderPage.class)
                .verifyPage()
                .verifyCityToDelivery(delivery_location)
                .inputStreet(street)
                .inputBuilding(building)
                .inputEntrance(entrance)
                .inputStair(stair)
                .inputApartment(apartment)
                .inputName(name)
                .inputSurname(surname)
                .inputMail(mail)
                .verifyProductName(product)
                .verifyProductPrice(this.productPrice)
                .clickPayButton()
                .verifyInfoMessage();
    }
}
