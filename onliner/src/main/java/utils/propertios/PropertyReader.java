package utils.propertios;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    public static Properties properties;

    @SneakyThrows
    public static void setUpProperties(String fileName) {
        properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/" + fileName + ".properties"));
    }

    public static Properties getProperties(){return properties;}

}
