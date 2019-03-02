
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.*;


public class FirstTest {
	private WebDriver driver;
	@BeforeTest
	private void initialize() {
		System.setProperty("webdriver.chrome.driver"
						  , "C:\\Users\\Andrew\\Desktop\\chromedriver_win32\\chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	private void getBeruRu() {
		driver.get("http://www.beru.ru");
		// close pop-up window
		try {
			driver.findElement(By.className("_1ZYDKa22GJ")).click();
		}
		catch(Exception e) {}
	}
	
	private void login() {
		// button for login
		driver.findElement((By.className("header2-nav__user"))).click();
		
		// login
		driver.findElement(By.id("passp-field-login")).sendKeys("testseleniumssu@yandex.com");
		driver.findElement(By.cssSelector("div.passp-button.passp-sign-in-button")).click();
		
		// password
		driver.findElement(By.id("passp-field-passwd")).sendKeys("andreyselenium");
		driver.findElement(By.cssSelector("div.passp-button.passp-sign-in-button")).click();
	}
	
	private void firstTest() {
		getBeruRu();
		login();
		WebElement profileButton = 
				driver.findElement(By.cssSelector("span.header2-nav-item__icon.header2-nav-item__icon_type_profile"));
		Assert.assertEquals(profileButton.getAttribute("title"), "Мой профиль");
		driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D3877%26loggedin%3D1");
	}
	
	@Test
	private void secondTest() {
		getBeruRu();
		WebElement cityInfo = driver.findElement(By.className("unique-selling-proposition-line__item"));
		cityInfo.findElement(By.className("link__inner")).click();
		WebElement popupForm = driver.findElement(By.className("header2-region-popup"));
		popupForm.findElement(By.className("input__control")).sendKeys("Хвалынск");
		popupForm.findElement(By.className("button2")).click();
	}
	
	
	
}
