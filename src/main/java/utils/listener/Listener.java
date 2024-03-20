package utils.listener;

import lombok.extern.log4j.Log4j;
import org.testng.ITestContext;
import org.testng.ITestListener;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Configuration.browser;
import static utils.properties.PropertyReader.getProperties;
import static utils.properties.PropertyReader.setUpProperties;

@Log4j
public class Listener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
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
        browser = getProperties().getProperty("browser");
        headless = Boolean.parseBoolean(getProperties().getProperty("headless"));
    }

}
