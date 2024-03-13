package utils.listener;

import com.codeborne.selenide.Configuration;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestListener;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Configuration.browser;
import static utils.logs.Logs.clearLogDirectory;
import static utils.propertios.PropertyReader.getProperties;
import static utils.propertios.PropertyReader.setUpProperties;

@Log4j
public class Listener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        clearLogDirectory();
        if (System.getProperties().containsKey("config")) {
            setUpProperties(System.getProperty("config"));
            setUpSelenideConfiguration();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Test finished " + context.getSuite().getName());
    }

    public void setUpSelenideConfiguration() {
        baseUrl = getProperties().getProperty("url");
        headless = Boolean.parseBoolean(getProperties().getProperty("headless"));
        timeout = Long.parseLong(getProperties().getProperty("timeout"));
        browser = getProperties().getProperty("browser");
        pageLoadTimeout = Long.parseLong(getProperties().getProperty("timeout_loading_page"));
        setOptions();
    }

    public void setOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-dev-shm-usage");
        options.addArguments("no-sandbox");
        options.addArguments("disable-extensions");
        options.addArguments("disable-cookies");
        Configuration.browserCapabilities = options;
    }
}
