package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AmazonCrawlPages {
    WebDriver driver;

    public AmazonCrawlPages() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nav-hamburger-menu")
    public WebElement hamburgerMenu;

    @FindBy(xpath = "(//div[text()='Kategoriye Göre Alışveriş Yap'])[1]")
    public WebElement shopByDepartments;

    @FindBy(xpath = "(//a[@aria-label='See all'])[1]/i")
    public WebElement seeALlDepartmentsList;

    @FindBy(xpath = "((//li[@class='hmenu-separator'])[1]//following-sibling::li[position() > 1 and position() <= 5]/a/i)|((//ul[@class='hmenu-compress-section'])[1]//a/i)")
    public List<WebElement> departments;

    @FindBy(xpath = "//ul[@class='hmenu hmenu-visible hmenu-translateX']/li/a")
    public List<WebElement> innerDepartments;

    @FindBy(xpath = "(//i[@class='nav-sprite hmenu-arrow-prev'])[1]")
    public WebElement mainMenu;

}
