package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReuseableMethods {

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            System.out.println("No waiting!");
        }
    }

    public static String getFirstNCharacters(String input, int n) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.length() >= n ? input.substring(0, n) : input;

    }
}