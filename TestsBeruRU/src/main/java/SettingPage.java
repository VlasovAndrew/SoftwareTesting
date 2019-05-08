import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SettingPage extends BasePage {

    @FindBy(className = "settings-list__title")
    private WebElement settingForm;
    private By lickCity = By.className("link__inner");

    public SettingPage(EventFiringWebDriver driver){
        super(driver);
    }

    public String getCityInSettings(){
        return settingForm.findElement(lickCity).getText();
    }

    public String getCityInTopPage(){
        return driver.findElement(lickCity).getText();
    }

}
