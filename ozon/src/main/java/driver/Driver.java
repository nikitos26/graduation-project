package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;

import static java.io.File.separator;
import static utils.propertios.PropertyReader.getProperties;

public class Driver {
    // ThreadLocal класс который хранит независимую копию переменной для работы в разных потоках
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void quitDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }

    public static void createDriver(DriverTypes type) {
        if (webDriver.get() == null) {
            switch (type) {
                case CHROME:
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(getProperties().getProperty("browser.options").split(";"));
                    options.setExperimentalOption("prefs", new HashMap<>() {{
                        put("profile.default_content_settings.popups", 0);
                        put("download.default_directory", System.getProperty("user.dir") + separator + "target");
                    }});
                    webDriver.set(new ChromeDriver(options));
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    webDriver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    webDriver.set(new EdgeDriver(edgeOptions));
            }
        }
    }
}