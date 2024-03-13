package pages;

import com.codeborne.selenide.DragAndDropOptions;
import static com.codeborne.selenide.Configuration.baseUrl;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class ProductPage {
    private final SelenideElement productPanel = $(By.cssSelector(".product.product_details.b-offers.js-product"));
    private final SelenideElement productName = productPanel.$(".catalog-masthead__title.js-nav-header");
    private final SelenideElement productMainPanel = productPanel.$(byClassName("product-main"));
    private final SelenideElement offersPanel = productMainPanel.$(byClassName("product-aside__offers"));
    private final SelenideElement deliveryCity = productMainPanel.$(byClassName("product-aside__delivery"));
    private final SelenideElement deliveryTooltipSuccessButton =  deliveryCity.$(byText(" Да, верно "));
    private final SelenideElement deliveryTooltipCancelButton =  deliveryCity.$(byText(" Нет, другой "));
    private final SelenideElement shortDescription = productMainPanel.$(byClassName("offers-description__specs"));
    private final SelenideElement price = productMainPanel.$(byClassName("js-description-price-link"));
    private final SelenideElement offersButton = productMainPanel.$(".button.button_orange.button_big");
    private final SelenideElement productSpecs = productMainPanel.$(".product-specs.js-product-specs");

    private final SelenideElement privacyPopup = $x("//div[@class='fc-dialog-container']");
    private final SelenideElement acceptButton = privacyPopup.$x("//div[@class='fc-dialog-container']");

    public ProductPage closePrivacyPopup(){
        switchTo().defaultContent();
        this.privacyPopup.shouldBe(exist);
        this.acceptButton.shouldBe(exist).click();
        return this;
    }

    public ProductPage verifyPageUrl(String url){
        log.error(webdriver().driver().url());
        Assert.assertEquals(url, webdriver().driver().url(), "Wrong page url");
        return this;
    }

    public ProductPage verifyPage(){
        this.productPanel.shouldBe(exist);
        this.productName.shouldBe(exist);
        this.price.shouldBe(exist);
        this.productSpecs.shouldBe(exist);
        this.offersButton.shouldBe(exist);
        log.info("Product page loaded");
        return this;
    }

    public ProductPage acceptAutoLocationDelivery(){
        this.deliveryTooltipSuccessButton.shouldBe(exist).click();
        log.info("Click on accept button in delivery tooltip");
        return this;
    }

    public ProductPage cancelAutoLocationDelivery(){
        this.deliveryTooltipCancelButton.shouldBe(exist).click();
        log.info("Click on cancel button in delivery tooltip");
        return this;
    }

    public ProductPage verifyProductName(String productName){
        Assert.assertTrue(this.productName.getText().toLowerCase().contains(productName.toLowerCase()),
                "Title on product page doesnt contain product name");
        return this;
    }

}
