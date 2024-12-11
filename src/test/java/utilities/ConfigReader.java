package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;

    static {
        String filePath = "config.properties";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties= new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Properties file could not be read!");
        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}