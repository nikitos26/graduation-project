package base;

import driver.Driver;
import lombok.Data;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


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
        elements.forEach(element -> waitLocatorAppear(element));
    }

    protected void pageAppear(By... locators) {
        for (By locator : locators) {
            waitLocatorAppear(locator);
        }
    }


    protected void navigateTo(String url) {
        driver.get(url);
    }
    protected void click(WebElement element) {
        element.click();
    }

    protected void click(By by) {
        click(driver.findElement(by));
    }

    protected void sendKeys(By by, CharSequence... charSequences) {
        sendKeys(driver.findElement(by), charSequences);
    }

    protected void sendKeys(WebElement element, CharSequence... charSequences) {
        element.clear();
        element.sendKeys(charSequences);
    }

    protected Integer getElementsCount(By by) {
        return driver.findElements(by).size();
    }

    @SneakyThrows
    protected void waitUntil(Integer seconds) {
        Thread.sleep(seconds * 1000);
    }

    protected void waitLocatorDisappear(By by) {
        waitLocatorDisappear(driver.findElement(by));
    }

    protected void waitLocatorDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitLocatorAppear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitLocatorAppear(By by) {
        waitLocatorAppear(driver.findElement(by));
    }

    protected String get_text(WebElement element) {
        return element.getText();
    }

    protected String get_text(By by) {
        return get_text(driver.findElement(by));
    }
}


