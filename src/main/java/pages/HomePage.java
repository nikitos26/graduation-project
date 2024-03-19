package pages;


import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static utils.properties.PropertyReader.getProperties;


@Log4j
public class HomePage {
    private final SelenideElement topNavigationPanel =  $(byClassName("b-top-menu"));
    private final SelenideElement userBar = $(byId("userbar"));
    protected final  SelenideElement middleNavigationPanel = $(byClassName("b-top-wrapper"));
    protected final  SelenideElement searchField = middleNavigationPanel.$(byCssSelector(".fast-search__input"));
    protected final  SelenideElement cartIcon = middleNavigationPanel.$(byCssSelector(".auth-bar__item--cart"));
    private final SelenideElement logo = $(byClassName("onliner_logo"));
    private final SelenideElement informationPanel = $(byClassName("g-middle-i"));

    public HomePage openHomePage() {
        String expectedUrl = "https://www.onliner.by/";
        String currentUrl = url();
        log.info("Open url - > " + expectedUrl);
        if (!currentUrl.equals(expectedUrl)) {
            open(expectedUrl);
        }
        return this;
    }

    public HomePage verifyPageUrl(){
        this.topNavigationPanel.shouldBe(exist);
        this.userBar.shouldBe(exist);
        this.searchField.shouldBe(exist);
        this.logo.shouldBe(exist);
        this.informationPanel.shouldBe(exist);
        Assert.assertEquals(webdriver().driver().url(), getProperties().getProperty("url"), "Wrong home page url.");
        return this;
    }

    public HomePage searchProductInSearchBar(String product){
        this.searchField.should(visible).sendKeys(product);
        return this;
    }

    public HomePage checkCartIcon() {
        this.cartIcon.$(byCssSelector(".auth-bar__counter")).shouldBe(exist);
        return this;
    }
}
