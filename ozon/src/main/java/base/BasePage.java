package base;

import driver.Driver;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Log4j
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    {
        driver = Driver.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        actions = new Actions(driver);
    }

    protected void pageAppear(List<WebElement> elements){
        log.info("Waiting for page to load ...");
        elements.forEach(element -> waitLocatorAppear(element));
    }

    protected void pageAppear(By... locators) {
        log.info("Waiting for page to load ...");
        for (By locator : locators) {
            waitLocatorAppear(locator);
        }
    }


    protected void navigateTo(String url) {
        log.info("Open url - > " + url);
        driver.get(url);
    }
    protected void click(WebElement element) {
        log.info("Click on element - > " + element);
        element.click();
    }

    protected void click(By by) {
        click(driver.findElement(by));
    }

    protected void sendKeys(By by, CharSequence... charSequences) {
        log.info("Enter text in element -> " + by);
        sendKeys(driver.findElement(by), charSequences);
    }

    protected void sendKeys(WebElement element, CharSequence... charSequences) {
        log.info("Clear text in element - > " + element);
        element.clear();
        log.info("Enter text in element -> " + element);
        element.sendKeys(charSequences);
    }

    protected Integer getElementsCount(By by) {
        log.info("Getting size of element -> " + by);
        return driver.findElements(by).size();
    }

    @SneakyThrows
    protected void waitUntil(Integer seconds) {
        log.warn("Waiting ...");
        Thread.sleep(seconds * 1000);
    }

    protected void waitLocatorDisappear(By by) {
        waitLocatorDisappear(driver.findElement(by));
    }

    protected void waitLocatorDisappear(WebElement element) {
        log.info("Wait for disappear element -> " + element);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitLocatorAppear(WebElement element) {
        log.info("Wait element appear -> " + element);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitLocatorAppear(By by) {
        waitLocatorAppear(driver.findElement(by));
    }

    protected String get_text(WebElement element) {
        log.info("Getting text of element -> " + element);
        return element.getText();
    }

    protected String get_text(By by) {
        return get_text(driver.findElement(by));
    }
}


