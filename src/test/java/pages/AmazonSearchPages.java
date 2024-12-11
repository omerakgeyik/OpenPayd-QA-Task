package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class AmazonSearchPages {
    WebDriver driver;

    public AmazonSearchPages() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nav-logo")
    public WebElement amazonLogo;

    @FindBy(id = "a-autoid-1")
    public WebElement cookieClose;

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(xpath = "//h2[text()='Sonuçlar']")
    public WebElement result;

    @FindBy(css = "div.s-result-item.s-asin:not(.AdHolder):not(:has(div.puis-see-details-content)):not(:has(span[data-a-strike=true]))")
    public List<WebElement> products;

    @FindBy(xpath = "//div[contains(@class, 's-result-item') and contains(@class, 's-asin') and not(contains(@class, 'AdHolder')) and not(.//div[contains(@class, 'puis-see-details-content')]) and not(.//span[@data-a-strike='true'])]//button[contains(@id, 'a-autoid-')]")
    public List<WebElement> addProduct;

    @FindBy(xpath = "//div[contains(@class, 's-result-item') and contains(@class, 's-asin') and not(contains(@class, 'AdHolder')) and not(.//div[contains(@class, 'puis-see-details-content')]) and not(.//span[@data-a-strike='true'])]//div[@data-cy='title-recipe']//h2")
    public List<WebElement> productName;

    @FindBy(xpath = "(//div[@class = 'a-popover-wrapper']//button[text() = 'Sepete ekle'])[1]")
    public WebElement popupAddButton;

    @FindBy(id = "nav-cart")
    public WebElement cart;

    @FindBy(xpath = "//h4//span[@class='a-truncate-cut']")
    public List<WebElement> productTitleInCart;
}
