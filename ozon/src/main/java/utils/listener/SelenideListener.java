package utils.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import static utils.logs.Logs.clearLogDirectory;
import static com.codeborne.selenide.Configuration.*;
import static utils.propertios.PropertyReader.setUpProperties;
import static utils.propertios.PropertyReader.getProperties;

public class SelenideListener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        clearLogDirectory();
        if (System.getProperties().containsKey("config")) {
            setUpProperties(System.getProperty("config"));
            setUpSelenideConfiguration();
        }
    }

    public void setUpSelenideConfiguration() {
        baseUrl = getProperties().getProperty("baseUrl");
        headless = Boolean.parseBoolean(getProperties().getProperty("headless"));
        timeout = Long.parseLong(getProperties().getProperty("timeout"));
    }
}
