package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AmazonSearchPages;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReuseableMethods;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilities.Driver.driver;

public class AmazonSearchStepDef {

    AmazonSearchPages pages = new AmazonSearchPages();
    Actions actions = new Actions(Driver.getDriver());
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    List<String> searchProductNames = new ArrayList<>();
    List<String> cartProductNames = new ArrayList<>();

    @Given("go to the Amazon homepage")
    public void goToTheAmazonHomepage() {
        Driver.getDriver().get(ConfigReader.getProperty("amazonUrl"));
        ReuseableMethods.sleep(2);
        pages.cookieClose.click();
        assertTrue(pages.amazonLogo.isDisplayed());
    }

    @When("search for {string} is performed")
    public void searchForIsPerformed(String searchText) {
        ReuseableMethods.sleep(2);
        pages.searchBox.sendKeys(searchText, Keys.ENTER);
        ReuseableMethods.sleep(2);
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        assertTrue(pages.result.isDisplayed());
        ReuseableMethods.sleep(2);
    }

    @When("all non-discounted products in stock on the first page are added to the cart")
    public void allNonDiscountedProductsInStockOnTheFirstPageAreAddedToTheCart() {
        // If you want all products on the page to be added to the cart:
        // Replace the number 5 in the i < 5 expression in the for loop with:
        // "pages.products.size()"

        for (int i = 0; i < 5; i++){
            try {
                wait.until(ExpectedConditions.visibilityOf(pages.products.get(i)));
                actions.moveToElement(pages.products.get(i));
                String pageProductName = pages.productName.get(i).getText();
                String shortedPageProductName = ReuseableMethods.getFirstNCharacters(pageProductName,20);
                searchProductNames.add(shortedPageProductName);
                ReuseableMethods.sleep(1);
                pages.addProduct.get(i).click();
                ReuseableMethods.sleep(1);

                if (!driver.findElements(By.xpath("//div[@class='a-popover-wrapper']")).isEmpty()) {
                    ReuseableMethods.sleep(2);
                    wait.until(ExpectedConditions.elementToBeClickable(pages.popupAddButton));
                    pages.popupAddButton.click();
                    ReuseableMethods.sleep(2);
                }
            } catch (TimeoutException e) {
                System.out.println("An operation timed out: " + e.getMessage());
            }
        }
    }

    @And("the cart is opened")
    public void theCartIsOpened() {
        ReuseableMethods.sleep(2);
        actions.moveToElement(pages.cart);
        pages.cart.click();
        ReuseableMethods.sleep(2);
    }

    @Then("the correct products should be listed in the cart")
    public void theCorrectProductsShouldBeListedInTheCart() {
        for (WebElement product : pages.productTitleInCart){
            String cartProductName = product.getText();
            String shortedCartProductName = ReuseableMethods.getFirstNCharacters(cartProductName, 20);
            cartProductNames.add(shortedCartProductName);
        }
        ReuseableMethods.sleep(2);
        Collections.reverse(cartProductNames);

        for (int i = 0; i < pages.productTitleInCart.size(); i++){
            assertEquals(searchProductNames.get(i), cartProductNames.get(i));
        }
        Driver.quitDriver();
    }
}
