import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static EventFiringWebDriver driver;

    @BeforeMethod
    protected void initialization() {
        System.setProperty("webdriver.chrome.driver"
                          , "C:\\Users\\Andrew\\Desktop\\chromedriver_win32\\chromedriver.exe");

        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new ListenerWithScreenShoter());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

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
