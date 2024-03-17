package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class OffersPage extends ProductPage {
    private final SelenideElement offersPanel = $x("//div[@class='offers-filter']");
    private final SelenideElement offersFilter = $x("//div[@class='offers-filter__part offers-filter__part_1']");
    private final ElementsCollection offersList = $$(byCssSelector(".offers-list__item"));
    private final SelenideElement deliveryPanel = $(".offers-list__sorting");
    private final SelenideElement deliveryLocation = $(byCssSelector(".offers-list__link_primary-alter.offers-list__link_base"));
    private final SelenideElement deliveryTooltipAcceptButton = deliveryPanel.$(Selectors.byText("Да, верно"));
    private final SelenideElement deliveryTooltipCancelButton = deliveryPanel.$(Selectors.byText("Нет, другой"));


    public OffersPage verifyOffersPage() {
        super.productName.shouldBe(exist);
        this.offersPanel.shouldBe(exist);
        this.offersFilter.shouldBe(exist);
        this.deliveryPanel.shouldBe(exist);
        this.deliveryLocation.shouldBe(exist);
        log.info("Offers page opened");
        return this;
    }

    public OffersPage closeDeliveryTooltip(String location) {
        if (this.deliveryLocation.getText().equals(location)) {
            this.deliveryTooltipAcceptButton.click();
            log.info("Location the same sa in product page");
            return this;
        }
        this.deliveryTooltipCancelButton.shouldBe(exist).click();
        super.changePlaceInDeliveryPopup(location);
        log.info("Close delivery popup");
        return this;
    }

    @SneakyThrows
    public OffersPage addCheapestOfferInBasket() {
        int cheapestPrice = Integer.MAX_VALUE;
        SelenideElement addToBasket = null;


        this.offersList.shouldHave(sizeGreaterThan(0));

        for (SelenideElement offer : this.offersList) {

            String offerPriceStr = offer.$(byCssSelector(".offers-list__description_nodecor")).getText();
            int price = Integer.parseInt(offerPriceStr.replaceAll("[^0-9]", ""));

            if (price < cheapestPrice) {
                cheapestPrice = price;
                addToBasket = offer.$(byCssSelector(".offers-list__control_default.helpers_hide_tablet"))
                        .$(byText("В корзину"));
            }

            SelenideElement infoTooltip = offer.$(byText("Все ясно, спасибо"));
            if (infoTooltip.exists()) {
                log.info("Close info popup.");
                infoTooltip.scrollIntoView(true).click();
            }
        }
        log.info("Add product in basket");
        addToBasket.scrollIntoView(true).click();
        return this;
    }

    public OffersPage verifyCartCounterAppear() {
        super.cartIcon.$(byCssSelector(".auth-bar__counter")).shouldBe(exist);
        return this;
    }

    public OffersPage clickOnCart() {
        super.cartIcon.scrollIntoView(true).click();
        return this;
    }

    @SneakyThrows
    public OffersPage closeSideBar() {
        sleep(1000);     // need to wait open side bar
        if (super.recommendSideBar.getSideBar().exists()) {
            super.recommendSideBar.closeSideBar();
            log.info("Side Bar was closed");
            return this;
        }
        return this;
    }

    public OffersPage closePrivacyPopup(){
        super.closePrivacyPopup();
        return this;
    }


}