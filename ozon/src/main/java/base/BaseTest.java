package base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import utils.listener.Listener;

import static driver.Driver.createDriver;
import static driver.Driver.quitDriver;
import static driver.DriverTypes.CHROME;
import static driver.DriverTypes.valueOf;
import static utils.propertios.PropertyReader.getProperties;

@Listeners(Listener.class)
public class BaseTest {

    @BeforeTest
    protected void setUp() {
        createDriver(System.getProperties().containsKey("config")
                ? valueOf(getProperties().getProperty("browser").toUpperCase())
                : CHROME
        );
    }

    @AfterTest(alwaysRun = true)
    protected void tearDown() {
        quitDriver();
    }
}
