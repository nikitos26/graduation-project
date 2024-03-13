package pages;

import static com.codeborne.selenide.Condition.exist;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;


import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class SearchPopup {
    private final SelenideElement popup = $(byClassName("modal-iframe"));
    private final SelenideElement loading = $(byClassName("search__bar_searching"));
    private final SelenideElement searchResultsPanel = $(byClassName("search__results"));
    private final ElementsCollection searchResults = searchResultsPanel.findAll(".search__result");
    private final SelenideElement inputField = $(byClassName("search__input"));
    @Getter
    private String productUrl;

    public SearchPopup validateSearchPopup(){
        this.popup.shouldBe(exist);
        webdriver().driver().switchTo().frame(popup);
        this.loading.shouldNotBe(exist);
        this.inputField.shouldBe(exist);
        this.searchResultsPanel.shouldBe(exist);
        log.info("Search Popup has opened");
        return this;
    }

    public SearchPopup switchToDefaultFrame(){
        switchTo().defaultContent();
        return this;
    }

    public SearchPopup searchProductInResults(String product){
        if(this.searchResults.isEmpty()) {
            Assert.assertFalse(this.searchResults.isEmpty(), "Search list is empty");
        }
        boolean isProductHasInResults = false;

        for (SelenideElement result : this.searchResults) {
            SelenideElement title = result.$(byClassName("product__title-link"));
            System.out.println(title.getText().toLowerCase());
            System.out.println(product.toLowerCase());
            if ((title.getText().toLowerCase()).contains(product.toLowerCase())) {
                isProductHasInResults = true;
                break;
            }
        }
        String message = "Product " + product + " is not in the results list";
        Assert.assertTrue(isProductHasInResults, message);
        return this;
    }

    public SearchPopup clickOnProductInResults(String product){
        for (SelenideElement result : this.searchResults) {
            SelenideElement title = result.$(byClassName("product__title-link"));
            if ((title.getText().toLowerCase()).contains(product.toLowerCase())) {
                this.productUrl = title.getAttribute("href");
                log.info("Click on product in results");
                title.click();
//                switchTo().defaultContent();
                return this;
            }
        }
        log.warn("Product not find in results");
        webdriver().driver().switchTo().defaultContent();
        return this;
    }

}
