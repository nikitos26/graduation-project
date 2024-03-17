package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j
public class PlacingOrderPage {
    private final SelenideElement header = $(byCssSelector(".cart-form__title_condensed-other"));
    private final SelenideElement cityToDelivery = $x("//input[@placeholder='Укажите населенный пункт']");

    private final String deliveryAddressPanel = "//div[@class='cart-form__step-list']/div[1]/div[3]/div/div[4]";
    private final SelenideElement street = $(byXpath(deliveryAddressPanel + "/div[1]/div[2]/div/div/div/div/div/input"));
    private final SelenideElement building = $(byXpath(deliveryAddressPanel + "/div[3]/div[2]/div/input"));
    private final SelenideElement corps = $x(deliveryAddressPanel + "/div[4]/div[2]/div/input");
    private final SelenideElement entrance = $x(deliveryAddressPanel + "/div[6]/div[2]/div/input");
    private final SelenideElement stair = $x(deliveryAddressPanel + "/div[7]/div[2]/div/input");
    private final SelenideElement apartment = $x(deliveryAddressPanel + "/div[8]/div[2]/div/input");

    private final String descriptionPanel = "//div[@class='cart-form__step-list']/div[1]/div[3]/div/div[5]";
    private final SelenideElement descriptionField = $x(descriptionPanel + "/div/div[2]/div/textarea");

    private final String contactsPanel = "//div[@class='cart-form__step-list']/div[1]/div[3]/div/div[9]";
    private final SelenideElement name = $x(contactsPanel + "/div/div[2]/div/input");
    private final SelenideElement surname = $x(contactsPanel + "/div[2]/div[2]/div/input");

    private final String emailPanel = "//div[@class='cart-form__step-list']/div[1]/div[3]/div/div[10]";
    private final SelenideElement email = $x(emailPanel + "/div/div[2]/div/input");

    private final SelenideElement payButton = $(".cart-form__button_responsive");

    private final SelenideElement descriptionOrderPanel = $(byXpath("//div[@class='cart-form__description cart-form__description_primary cart-form__description_base cart-form__description_condensed-complementary cart-form__description_nonadaptive']"));
    private final SelenideElement productDescription = descriptionOrderPanel
            .$(byCssSelector(".cart-form__description-part.cart-form__description-part_1"));
    private final SelenideElement productPrice = descriptionOrderPanel
            .$(byCssSelector(".cart-form__description_base.cart-form__description_ellipsis"));

    private final SelenideElement infoMessage = $(byCssSelector(".growl.growl-error.growl-medium"));

    public PlacingOrderPage verifyPage() {
        log.info("Verify order page");
        this.name.shouldBe(exist);
        this.street.shouldBe(exist);
        this.surname.shouldBe(exist);
        this.email.shouldBe(exist);
        return this;
    }

    public PlacingOrderPage inputStreet(String street) {
        log.info("Input street -> " + street);
        this.street.shouldBe(exist).scrollIntoView(true).sendKeys(street);
        return this;
    }

    public PlacingOrderPage inputBuilding(String building) {
        log.info("Input building -> " + building);
        this.building.shouldBe(exist).scrollIntoView(true).sendKeys(building);
        return this;
    }

    public PlacingOrderPage inputApartment(String apartment) {
        log.info("Input apartment -> " + apartment);
        this.apartment.shouldBe(exist).scrollIntoView(true).sendKeys(apartment);
        return this;
    }

    public PlacingOrderPage inputCorps(String corps) {
        log.info("Input corps -> " + corps);
        this.corps.shouldBe(exist).scrollIntoView(true).sendKeys(corps);
        return this;
    }

    public PlacingOrderPage inputEntrance(String entrance) {
        log.info("Input entrance -> " + entrance);
        this.entrance.shouldBe(exist).scrollIntoView(true).sendKeys(entrance);
        return this;
    }

    public PlacingOrderPage inputStair(String stair) {
        log.info("Input stair -> " + stair);
        this.stair.shouldBe(exist).scrollIntoView(true).sendKeys(stair);
        return this;
    }

    public PlacingOrderPage inputName(String name) {
        log.info("Input name -> " + name);
        this.name.shouldBe(exist).scrollIntoView(true).sendKeys(name);
        return this;
    }

    public PlacingOrderPage inputSurname(String surname) {
        log.info("Input surname -> " + surname);
        this.surname.shouldBe(exist).scrollIntoView(true).sendKeys(surname);
        return this;
    }

    public PlacingOrderPage inputMail(String mail) {
        log.info("Input mail -> " + mail);
        this.email.shouldBe(exist).scrollIntoView(true).sendKeys(mail);
        return this;
    }

    public PlacingOrderPage clickPayButton() {
        log.info("Click pay button ");
        this.payButton.shouldBe(exist).scrollIntoView(true).click();
        return this;
    }

    public PlacingOrderPage verifyCityToDelivery(String deliveryLocation) {
        log.info("Verify city to delivery");
        Assert.assertTrue(this.cityToDelivery.getValue().toLowerCase().contains(deliveryLocation.toLowerCase()),
                "Wrong city to delivery");
        return this;
    }

    public PlacingOrderPage verifyProductName(String productName) {
        log.info("Verify product name");
        Assert.assertTrue(this.productDescription.getText().toLowerCase().contains(productName.toLowerCase()),
                "Wrong product name");
        return this;
    }

    public PlacingOrderPage verifyProductPrice(int price) {
        log.info("Verify product price");
        Integer productPriceInt = Integer.parseInt(this.productPrice.getText().replaceAll("[, р.]", ""));
        Assert.assertTrue(productPriceInt.equals(price),
                "Wrong product price");
        return this;
    }

    public PlacingOrderPage verifyInfoMessage() {
        log.info("Verify info tooltip");
        this.infoMessage.shouldBe(exist);
        return this;
    }


}
