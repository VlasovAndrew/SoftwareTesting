import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SettingPage extends BasePage {

    @FindBy(className = "settings-list__title")
    private WebElement settingForm;
    private By lickCity = By.className("link__inner");

    public SettingPage(EventFiringWebDriver driver){
        super(driver);
    }

    @Step(value = "Получение города в настройках")
    public String getCityInSettings(){
        return settingForm.findElement(lickCity).getText();
    }

    @Step(value = "Получение города в верху страницы")
    public String getCityInTopPage(){
        return driver.findElement(lickCity).getText();
    }
}
