package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AmazonCrawlPages;
import utilities.Driver;
import utilities.ReuseableMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AmazonCrawlStepDef {

    AmazonCrawlPages pages = new AmazonCrawlPages();
    Actions actions = new Actions(Driver.getDriver());
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    BufferedWriter writer;

    List<String> departmentLinks = new ArrayList<>();
    List<String> urlLinks = new ArrayList<>();

    String url;
    String title;
    String status;

    @When("navigate to the dropdown menu")
    public void navigateToTheDropdownMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(pages.hamburgerMenu));
        pages.hamburgerMenu.click();
        wait.until(ExpectedConditions.visibilityOf(pages.shopByDepartments));
    }

    @Then("see all department links")
    public void seeAllDepartmentLinks() {
        ReuseableMethods.sleep(2);
        wait.until(ExpectedConditions.visibilityOf(pages.seeALlDepartmentsList));
        pages.seeALlDepartmentsList.click();
    }

    @And("verify each link is not dead")
    public void verifyEachLinkIsNotDead() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        writer = new BufferedWriter(new FileWriter(timestamp + "_results.txt"));

        for (WebElement link : pages.departments){
            actions.moveToElement(link).perform();
            wait.until(ExpectedConditions.elementToBeClickable(link));
            link.click();

            for (int i = 1; i < pages.innerDepartments.size(); i++){
                wait.until(ExpectedConditions.visibilityOf(pages.innerDepartments.get(i)));
                url = pages.innerDepartments.get(i).getAttribute("href");
                ReuseableMethods.sleep(4);
                departmentLinks.add(url);
                title = pages.innerDepartments.get(i).getText();
                if (url != null && !url.isEmpty()) {
                    ReuseableMethods.sleep(1);
                    status = checkLink(url);
                    ReuseableMethods.sleep(1);
                    writer.write(url + ", " + title + ", " + status);
                    writer.newLine();
                }
            }
            writer.close();
            try{
                ReuseableMethods.sleep(2);
                wait.until(ExpectedConditions.visibilityOf(pages.mainMenu));
                actions.moveToElement(pages.mainMenu).perform();
                pages.mainMenu.click();
                ReuseableMethods.sleep(2);
            }catch (ElementClickInterceptedException exception){
                System.out.println("Exception: " + exception.getMessage());
            }
        }
    }

    private String checkLink(String link) {
        WebDriver tempDriver = null;
        try {
            tempDriver = new ChromeDriver();
            tempDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            tempDriver.get(link);

            urlLinks.add(tempDriver.getCurrentUrl());
            HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println("ResponseCode: " + responseCode);
            return (responseCode >= 200 && responseCode < 400) ? "OK - STATUS: " + responseCode : "Dead link - STATUS: " + responseCode;

        } catch (Exception e) {
            return "Dead link - Error occurred";
        } finally {
            if (tempDriver != null) {
                tempDriver.quit();
                ReuseableMethods.sleep(6);
            }
        }
    }

    @And("verify the links")
    public void verifyTheLinks() {
        for (String departmentLink : departmentLinks) {
            assertEquals(departmentLink, urlLinks.get(0));
        }
        Driver.quitDriver();
    }
}
