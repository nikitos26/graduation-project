package utils.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;

import static utils.propertios.PropertyReader.setUpProperties;

public class Listener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        if (System.getProperties().containsKey("config")) {
            setUpProperties(System.getProperty("config"));
        }
    }
    
}
