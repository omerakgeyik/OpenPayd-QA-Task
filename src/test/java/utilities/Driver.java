package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.time.Duration;
import java.util.List;

public class Driver {
    private Driver(){
    }
    public static WebDriver driver;

    public static WebDriver getDriver(){
        String browser = ConfigReader.getProperty("browser");
        if (driver == null){
            switch (browser){
                case "firefox" :
                    driver = new FirefoxDriver();
                    break;
                case "safari" :
                    driver = new SafariDriver();
                    break;
                case "edge" :
                    driver = new EdgeDriver();
                    break;
                default:
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                    options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                    driver = new ChromeDriver(options);
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }
    public static void quitDriver(){
        driver.quit();
        driver = null;
    }
}
