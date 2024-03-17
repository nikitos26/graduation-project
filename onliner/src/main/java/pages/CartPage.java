package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

@Log4j
public class CartPage {
    private final SelenideElement page = $(byClassName("cart-form__body"));
    private final SelenideElement pageTitle = page.$(byCssSelector(".cart-form__title"));
    private final SelenideElement productPanel = page.$(byCssSelector(".cart-form__offers-list"));
    private final ElementsCollection products = productPanel.$$(byCssSelector(".cart-form__offers-unit_primary"));
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

}
