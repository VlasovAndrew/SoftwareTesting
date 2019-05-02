import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    @FindBy(className = "header2-nav__user")
    private WebElement profileBtn;

    @FindBy(className = "link__inner")
    private WebElement cityLink;

    @FindBy(className = "header2-user-menu__item_type_settings")
    private WebElement settingsBtn;

    @FindBy(className = "header2__navigation")
    private WebElement catalogBtn;

    @FindBy(className = "header2-menu")
    private WebElement basketBtn;

    public MainPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public MainPage login(){
        profileBtn.click();
        this.inputLogin();
        this.inputPassword();
        return new MainPage(this.driver);
    }

    private  void inputLogin(){
        driver.findElement(By.id("passp-field-login")).sendKeys(Options.LOGIN);
        driver.findElement(By.className("passp-sign-in-button")).click();
    }

    private void inputPassword(){
        driver.findElement(By.id("passp-field-passwd")).sendKeys(Options.PASSWORD);
        driver.findElement(By.cssSelector("div.passp-button.passp-sign-in-button")).click();

    }

    public String getLogitBtnText(){
        return profileBtn.getText();
    }
    public String getCityName(){
        return cityLink.getText();
    }

    public MainPage setCity(String city){
        cityLink.click();
        WebElement popupForm = driver.findElement(By.className("header2-region-popup"));
        popupForm.findElement(By.className("input__control")).sendKeys(city);
        popupForm.findElement(By.className("region-suggest__list-item")).click();
        popupForm.findElement(By.className("button2")).click();
        waitElementStaleness(popupForm);
        return new MainPage(driver);
    }

    public SettingPage clickOnSettings(){
        this.profileBtn.click();
        this.settingsBtn.click();
        return new SettingPage(driver);
    }

    public ProductPage goToCategory(String mainCategory, String subcategory){
        catalogBtn.click();
        WebElement formCategory = driver.findElement(By.className("n-navigation-vertical-category"));
        WebElement element = formCategory.findElement(By.linkText(mainCategory));
        action.moveToElement(element).perform();
        WebElement formMenu = driver.findElement(By.className("n-navigation-vertical-menu"));
        action.moveToElement(catalogBtn).perform();
        formMenu.findElement(By.linkText(subcategory)).click();
        return new ProductPage(driver);
    }

    public BasketPage goToBasket(){
        this.basketBtn.click();
        return new BasketPage(driver);
    }

}
