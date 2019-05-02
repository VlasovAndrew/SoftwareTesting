import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected Wait<WebDriver> wait;
    protected EventFiringWebDriver driver;
    protected Actions action;

    public BasePage(EventFiringWebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
        this.action = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    protected void logout(){
        try{
            driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D3877%26loggedin%3D1");
        }
        catch (Exception e){}
    }

    protected int getPriceFromString(String text){
        String number = text.replaceAll("[^0-9\\-]", "");
        if (number.length() == 0){
            return 0;
        }
        return Integer.parseInt(number);
    }


    protected void waitPageLoad(){
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver)
                                .executeScript("return document.readyState").equals("complete");
                    }
                };
        wait.until(pageLoadCondition);
    }
    protected void waitElementStaleness(WebElement element){
        try{
            wait.until(ExpectedConditions.stalenessOf(element));
        }
        catch (Exception e){}
    }

}
