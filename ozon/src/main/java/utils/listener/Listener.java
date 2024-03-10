package utils.listener;

import lombok.extern.log4j.Log4j;
import org.testng.ITestContext;
import org.testng.ITestListener;

import static utils.logs.Logs.clearLogDirectory;
import static utils.propertios.PropertyReader.setUpProperties;

@Log4j
public class Listener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        clearLogDirectory();
        if (System.getProperties().containsKey("config")) {
            setUpProperties(System.getProperty("config"));
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Test finished " + context.getSuite().getName());
    }

}
