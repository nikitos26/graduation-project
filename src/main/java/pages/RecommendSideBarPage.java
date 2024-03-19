package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j
@Getter
public class RecommendSideBarPage {

    private final SelenideElement sideBar = $(byCssSelector(".product-recommended__sidebar-overflow"));
    private final SelenideElement closeButton = $(byCssSelector(".product-recommended__sidebar-close"));
    private final SelenideElement header = sideBar.find(byXpath("product-recommended__subheader"));
    private final SelenideElement productDescriptionPanel = $x("//div[@class='product-recommended__list product-recommended__list_alter']");
    private final SelenideElement productDescription = productDescriptionPanel
            .$x("//div[@class='product-recommended__link product-recommended__link_primary']");
    private final SelenideElement productPrice = productDescriptionPanel.$(byCssSelector(".product-recommended__price"))
            .$(byCssSelector(".product-recommended__link.product-recommended__link_primary"));
    private final SelenideElement addToCart = $(byCssSelector(".button-style_another.button-style_base-alter"));


    public RecommendSideBarPage verifySideBar() {
        log.info("Verify Recommend Side Bar");
        this.sideBar.shouldBe(exist);
        this.header.shouldBe(exist);
        return this;
    }

    public RecommendSideBarPage closeSideBar() {
        log.info("Close Recommend Side Bar");
        this.closeButton.shouldBe(exist).click();
        this.closeButton.shouldNotBe(exist);
        return this;
    }


}
