package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class ProductPage extends HomePage {
    private final SelenideElement productPanel = $(byCssSelector(".b-offers.js-product"));
    protected final SelenideElement productName = productPanel.$(".catalog-masthead__title.js-nav-header");
    private final SelenideElement productMainPanel = productPanel.$(byCssSelector(".product-main"));
    private final SelenideElement offersPanel = productMainPanel.$(byClassName("product-aside__offers"));
    private final SelenideElement deliveryCityPanel = productMainPanel.$(byCssSelector(".product-aside__delivery"));
    private final SelenideElement deliveryTooltipSuccessButton = deliveryCityPanel.$(byText("Да, верно"));
    private final SelenideElement deliveryTooltipCancelButton = deliveryCityPanel.$(byText("Нет, другой"));
    private final SelenideElement shortDescription = productMainPanel.$(byClassName("offers-description__specs"));
    protected final SelenideElement price = productMainPanel.$(byCssSelector(".js-description-price-link"));
    private final SelenideElement offersButton = productMainPanel.$(byCssSelector(".button.button_orange.button_big"));
    private final SelenideElement productSpecs = productMainPanel.$(".product-specs.js-product-specs");

    private final SelenideElement privacyPopup = $x("//div[@class='fc-dialog-container']");
    private final SelenideElement acceptButton = privacyPopup.$(byText("Соглашаюсь"));
    private final SelenideElement deliveryLocation = this.deliveryCityPanel.$(byClassName("product-aside__link"));
    private final DeliveryPopup deliveryPopup = new DeliveryPopup();
    protected final RecommendSideBarPage recommendSideBar = new RecommendSideBarPage();

    public ProductPage closePrivacyPopup() {
        switchTo().defaultContent();
        this.privacyPopup.shouldBe(exist);
        this.acceptButton.shouldBe(exist).click();
        log.info("Privacy Popup closed");
        return this;
    }

    public ProductPage verifyPageUrl(String url) {
        log.info("url from search " + url);
        log.info("url from product " + webdriver().driver().url());
        Assert.assertEquals(url, webdriver().driver().url(), "Wrong page url");
        return this;
    }

    public ProductPage verifyPage() {
        this.productPanel.shouldBe(exist);
        this.productName.shouldBe(exist);
        this.price.shouldBe(exist);
        this.productSpecs.shouldBe(exist);
        this.offersButton.shouldBe(exist);
        log.info("Product page loaded");
        return this;
    }

    public ProductPage acceptAutoLocationDelivery() {
        this.deliveryTooltipSuccessButton.shouldBe(exist).click();
        log.info("Click on accept button in delivery tooltip");
        return this;
    }

    public ProductPage cancelAutoLocationDelivery() {
        this.deliveryTooltipCancelButton.shouldBe(exist).click();
        log.info("Click on cancel button in delivery tooltip");
        return this;
    }

    public ProductPage verifyProductName(String productName) {
        Assert.assertTrue(this.productName.getText().toLowerCase().contains(productName.toLowerCase()),
                "Title on product page doesnt contain product name");
        return this;
    }

    public ProductPage changeDeliveryPlace() {
        if (this.deliveryTooltipCancelButton.exists()) {
            this.deliveryTooltipCancelButton.click();
            return this;
        }
        deliveryLocation.click();
        return this;
    }

    public ProductPage changePlaceInDeliveryPopup(String location) {
        this.deliveryPopup.verifyPopup().chooseLocation(location);
        return this;
    }

    public ProductPage verifyChangedLocation(String location) {
        Assert.assertEquals(this.deliveryLocation.getText(), location, "Wrong changed location.");
        return this;
    }

    public ProductPage clickOffersButton() {
        this.offersButton.scrollIntoView(true).click();
        return this;
    }


}
