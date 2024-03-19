package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.regex.Pattern;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class CartPage {
    private final SelenideElement page = $(byClassName("cart-form__body"));
    private final SelenideElement pageTitle = page.$(byCssSelector(".cart-form__title"));
    private final SelenideElement productPanel = page.$(byCssSelector(".cart-form__offers-list"));
    private final ElementsCollection products = productPanel.$$(byCssSelector(".cart-form__offers-unit_primary"));
    private final SelenideElement inputField = $(byCssSelector(".cart-form__input_nonadaptive"));
    private final SelenideElement removeButton = $(byCssSelector(".cart-form__button_remove"));
    private final SelenideElement productDescription = $(byCssSelector(".cart-form__description_condensed-extra"));
    private Integer productPrice = 0;

    public CartPage verifyCartPage() {
        this.pageTitle.shouldHave(text("Корзина"));
        this.products.shouldHave(sizeGreaterThan(0));
        log.info("Cart page verified");
        return this;
    }

    public CartPage verifyProductInCart(String expectedProduct) {
        boolean isProductInCart = false;

        log.info("Search product in cart");
        for (SelenideElement product : this.products) {
            String actualProduct = product.$(byCssSelector(".cart-form__link_base-alter")).getText().toLowerCase();
            if (actualProduct.contains(expectedProduct.toLowerCase())) {
                isProductInCart = true;
            }
        }
        Assert.assertTrue(isProductInCart, "Search was found in cart");
        return this;
    }

    public CartPage increaseProductAmountByName(String productName) {
        log.info("Search product in cart to increase count");

        for (SelenideElement product : this.products) {
            String actualProduct = product.$(byCssSelector(".cart-form__link_base-alter")).getText().toLowerCase();
            if (actualProduct.contains(productName.toLowerCase())) {

                SelenideElement pricePanel = product.$(byCssSelector(".cart-form__offers-part_price_specific"));

                String productCostBeforeIncrease = pricePanel.
                        $(byCssSelector(".cart-form__description_ellipsis.cart-form__description_condensed")).getText();
                int PriceBeforeIncrease = Integer.parseInt(productCostBeforeIncrease.replaceAll("[^0-9]", ""));

                SelenideElement buttonPanel = product.$(byCssSelector(".cart-form__offers-part_count"));
                buttonPanel.$(byCssSelector(".cart-form__button_increment")).click();

                log.info("Product was increased");
                pricePanel.$(byCssSelector(".cart-form__description_ellipsis.cart-form__description_condensed"))
                        .shouldNotHave(text(productCostBeforeIncrease));

                String productCostAfterIncrease = pricePanel
                        .$(byCssSelector(".cart-form__description_ellipsis.cart-form__description_condensed")).getText();
                int PriceAfterIncrease = Integer.parseInt(productCostAfterIncrease.replaceAll("[^0-9]", ""));

                Assert.assertEquals((PriceBeforeIncrease * 2), PriceAfterIncrease, "Wrong count products");
                log.info("The price is considered correct");
                this.productPrice = PriceAfterIncrease;
            }
        }
        return this;
    }

    public CartPage makeOrderByProductName(String productName) {
        for (SelenideElement product : this.products) {
            String actualProduct = product.$(byCssSelector(".cart-form__link_base-alter")).getText().toLowerCase();
            if (actualProduct.contains(productName.toLowerCase())) {
                SelenideElement additionalPanel = product.$(byCssSelector(".cart-form__offers-item_additional"));
                additionalPanel.$(byText("Перейти к оформлению")).click();
                log.info("Make order");
            }
        }
        return this;
    }

    public Integer getPrice() {
        return this.productPrice;
    }

    public CartPage inputTextInField(String text) {
        executeJavaScript("arguments[0].select();", inputField);
        this.inputField.sendKeys(Keys.BACK_SPACE);
        this.inputField.shouldBe(exist).sendKeys(text);


        return this;
    }

    public CartPage clickOnPrice() {
        this.pageTitle.shouldBe(exist).click();
        return this;
    }

    public CartPage verifyProductValue(String value) {
        Pattern patternChars = Pattern.compile("^[\\sA-zА-я.,!@#$%^&*()_+§±`'\"\\n-]+$");
        boolean isSymbolsAndChars = patternChars.matcher(value).matches();

        Pattern patternDigits = Pattern.compile("^[0-9]+$");
        boolean isDigits = patternDigits.matcher(value).matches();

        log.info(value + " matches is " + isSymbolsAndChars);
        log.info("value in field " + this.inputField.getValue());

        int valueInField = Integer.parseInt(this.inputField.getValue());


        if (isSymbolsAndChars) {
            Assert.assertTrue(valueInField == 1,
                    "Char or zero doesnt transform to 1");
            return this;
        }

        if (isDigits) {
            int valueInt = Integer.parseInt(value);

            if (valueInt > 99) {
                Assert.assertTrue(valueInField == 99,
                        "Value in field must be 99");
                return this;
            }

            if (valueInt <= 0) {
                Assert.assertTrue(valueInField == 1,
                        "Value in field must be 1");
                return this;
            }


            if (1 <= valueInt && valueInt <= 99) {
                Assert.assertTrue(valueInField == valueInt,
                        "User value doesnt equal value in field");
                return this;
            }
        }
        log.info("Value doesnt match");
        return this;
    }

    public CartPage validateAmountProducts(Integer size) {
        this.products.shouldHave(size(size));
        return this;
    }

    public CartPage validateRemovedProduct() {
        Assert.assertTrue(this.productDescription.getText().toLowerCase().contains("вы удалили"),
                "Product wasnt removed");
        this.inputField.shouldNotBe(exist);
        return this;
    }

    public CartPage removeProductFromCart() {
        actions().moveToElement(this.removeButton).doubleClick().perform();
        if (this.removeButton.exists()){
            actions().moveToElement(this.removeButton).doubleClick().perform();
        }
        return this;
    }

    public CartPage verifyProductDoesntExistInCart() {
        this.inputField.shouldNotBe(exist);
        this.productPanel.shouldNotBe(exist);
        this.productDescription.shouldNotBe(exist);
        return this;
    }

}
