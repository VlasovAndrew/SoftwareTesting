import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

@Listeners(StepWithAttachments.class)
public class BaseTest {

    protected static EventFiringWebDriver driver;

    @BeforeMethod
    protected void initialization() {
        System.setProperty("webdriver.chrome.driver"
                          , "C:\\Users\\Andrew\\Desktop\\chromedriver\\chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        getBeru();
    }

    private void getBeru() {
        driver.get(Options.ADDRESS);
        try {
            driver.findElement(By.className("_1ZYDKa22GJ")).click();
        } catch (Exception e) {}
    }

    @AfterMethod
    protected void makeLogout(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            ScreenShoter.attachScreenShot(driver);
        }
        new BasePage(driver).logout();
        driver.quit();
    }

    public static EventFiringWebDriver driver(){
        return driver;
    }

}
