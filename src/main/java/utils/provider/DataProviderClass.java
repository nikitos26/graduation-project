package utils.provider;
import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider(name = "Values to limit test")
    public Object[][] getUserNames() {
        return new Object[][]{{"0"},{" "},{""},{"100"},{"qwerty"},{"!@#"},{"0.1"},{"-10"},{"50"}};
    }
}
