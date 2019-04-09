import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import  io.qameta.allure.Step;

import java.util.concurrent.TimeUnit;

public class BeruWork {
    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    protected Actions action;

    protected String address = "http://www.beru.ru";

    protected String LOGIN = "testseleniumssu@yandex.com";
    protected String PASSWORD = "andreyselenium";

    protected By loginButton = By.className("header2-nav__user");

    @BeforeTest
    protected void initialize() {
        System.setProperty("webdriver.chrome.driver"
                          , "C:\\Users\\Andrew\\Desktop\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
        action = new Actions(driver);
    }

    protected void goOnBeruRu() {
        driver.get(address);
        // close pop-up window
        try {
            driver.findElement(By.className("_1ZYDKa22GJ")).click();
        } catch (Exception e) {}
    }

    @Step
    protected void login() {
        this.loginClick();
        this.inputLogin();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("passp-field-passwd")));
        this.inputPassword();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.loginButton));
    }

    private void loginClick(){
        driver.findElement(loginButton).click();
    }

    private  void inputLogin(){
        driver.findElement(By.id("passp-field-login")).sendKeys(LOGIN);
        driver.findElement(By.className("passp-sign-in-button")).click();
    }

    private void inputPassword(){
        driver.findElement(By.id("passp-field-passwd")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("div.passp-button.passp-sign-in-button")).click();
    }

    protected  void logout(){
        driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D3877%26loggedin%3D1");
        driver.quit();
    }


}
