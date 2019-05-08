import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected Wait<WebDriver> wait;
    protected static EventFiringWebDriver driver;
    protected Actions action;

    @FindBy(className = "header2-nav__user")
    protected WebElement profileBtn;

    @FindBy(className = "link__inner")
    protected WebElement cityLink;

    @FindBy(className = "header2-user-menu__item_type_settings")
    protected WebElement settingsBtn;

    @FindBy(className = "header2__navigation")
    protected WebElement catalogBtn;

    @FindBy(className = "header2-menu")
    protected WebElement basketBtn;

    @FindBy(className = "header2-user-menu__logout")
    protected WebElement logoutBtn;

    public BasePage(EventFiringWebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
        this.action = new Actions(driver);
        PageFactory.initElements(driver, this);
    }


    protected void logout(){
        if (getLogitBtnText().equals("Мой профиль")) {
            profileBtn.click();
            logoutBtn.click();
        }
    }

    protected int getPriceFromString(String text){
        String number = text.replaceAll("[^0-9\\-]", "");
        if (number.length() == 0){
            return 0;
        }
        return Integer.parseInt(number);
    }

    protected void waitElementStaleness(WebElement element){
        try{
            wait.until(ExpectedConditions.stalenessOf(element));
        }
        catch (Exception e){}
    }

    public static EventFiringWebDriver driver(){
        return driver;
    }

    public String getLogitBtnText(){
        return profileBtn.getText();
    }

    public String getCityName(){
        return cityLink.getText();
    }

}
