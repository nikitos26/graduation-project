package base;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Listeners;
import utils.listener.Listener;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.driver;

@Listeners(Listener.class)
public class BaseTest {
    protected <T> T get(Class<T> classPage) {
        if (driver().hasWebDriverStarted()) {
            return page(classPage);
        }
        return open(Configuration.baseUrl, classPage);
    }
}
