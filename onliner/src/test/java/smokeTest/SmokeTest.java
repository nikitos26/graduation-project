package smokeTest;

import base.BaseTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPopup;

public class SmokeTest extends BaseTest {


    @Test(priority = 1, description = "Verify Home Page")
    public void verifyHomePage(){
        get(HomePage.class).openHomePage();
    }

    @Test(priority = 2, dependsOnMethods = "verifyHomePage", description = "Try to find Iphone in search")
    @Parameters("product")
    public void findProductInCatalog(String product){
        get(HomePage.class).searchProductInSearchBar(product);
        get(SearchPopup.class)
                .validateSearchPopup()
                .searchProductInResults(product);
    }

    @Test(priority = 3, description = "Open product page", dependsOnMethods = "findProductInCatalog")
    @Parameters("product")
    public void openProductPage(String product){
        get(SearchPopup.class).clickOnProductInResults(product);
        String productUrl = get(SearchPopup.class).getProductUrl();
        get(ProductPage.class)
                .closePrivacyPopup()
                .verifyPage()
                .verifyPageUrl(productUrl)
                .verifyProductName(product);
    }

}
