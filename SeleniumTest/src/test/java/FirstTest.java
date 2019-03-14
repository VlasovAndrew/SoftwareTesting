
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTest {
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private JavascriptExecutor js;

    @BeforeTest
    private void initialize() {
        System.setProperty("webdriver.chrome.driver"
                , "C:\\Users\\Andrew\\Desktop\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        js = (JavascriptExecutor) driver;
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
        driver.findElement(By.className("passp-sign-in-button")).click();
        // password
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("passp-field-passwd")));
        driver.findElement(By.id("passp-field-passwd")).sendKeys("andreyselenium");
        driver.findElement(By.cssSelector("div.passp-button.passp-sign-in-button")).click();
    }

    private String getCurrentCity(){
        WebElement cityLink = driver.findElement(By.className("link__inner"));
        return cityLink.getText();
    }

    @Test
    private void firstTest() {
        getBeruRu();
        login();
        WebElement profileButton = driver.findElement(By.className("header2-nav-item__icon_type_profile"));
        Assert.assertEquals(profileButton.getAttribute("title"), "Мой профиль");
        driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D3877%26loggedin%3D1");
    }

    @Test
    private void secondTest() {
        getBeruRu();
        By cityLinkID = By.className("link_inner");
        driver.findElement(cityLinkID).click();
        WebElement popupForm = driver.findElement(By.className("header2-region-popup"));
        popupForm.findElement(By.className("input__control")).sendKeys("Хвалынск");
        popupForm.findElement(By.className("region-suggest__list-item")).click();
        popupForm.findElement(By.className("button2")).click();

        wait.until(ExpectedConditions.elementToBeClickable(cityLinkID));
        Assert.assertEquals(getCurrentCity(), "Хвалынск");

        login();
        driver.findElement(By.className("header2-nav__user")).click();
        driver.findElement(By.className("header2-user-menu__item_type_settings")).click();

        WebElement settingsForm = driver.findElement(By.className("settings-list__title"));
        String settingCity = settingsForm.findElement(cityLinkID).getText();
        Assert.assertEquals(getCurrentCity(), settingCity);

        driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D3877%26loggedin%3D1");
    }


    int getPriceFromString(String text){
        String number = text.replaceAll("[^0-9\\-]", "");
        if (number.length() == 0){
            return 0;
        }
        return Integer.parseInt(number);
    }


    void checkPrice(){
        wait.until(ExpectedConditions.elementToBeClickable(By.className("mshk8xBHT8")));
        List<WebElement> orderInfo = driver.findElements(By.className("_1Q9ASvPbPN"));
        ArrayList<Integer> price = new ArrayList<Integer>();
        for (WebElement raw : orderInfo){
            String[] items = raw.getText().split("\n");
            String title = items[0], cost = items[1];
            if (title.contains("Скидка")){
                cost = "-" + cost;
            }
            price.add(getPriceFromString(cost));
        }
        int totalSum = 0, lastIndex = price.size() - 1;
        for (int i = 0; i < lastIndex; i++){
            totalSum += price.get(i);
        }
        assert(totalSum == price.get(lastIndex));
    }

    private void selectCategory(String mainCategory, String subcategory){
        driver.findElement(By.className("header2__navigation")).click();
        WebElement formCategory = driver.findElement(By.className("n-navigation-vertical-category"));
        WebElement element = formCategory.findElement(By.linkText(mainCategory));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        WebElement formMenu = driver.findElement(By.className("n-navigation-vertical-menu"));
        formMenu.findElement(By.linkText(subcategory)).click();
    }

    private void setRange(int from, int to){
        driver.findElement(By.name("Цена от")).sendKeys(String.valueOf(from));
        driver.findElement(By.name("Цена до")).sendKeys(String.valueOf(to));
    }

    private void openAllList(){
        try {
            while(true){
                By showBtn = By.linkText("ПОКАЗАТЬ ЕЩЁ");
                wait.until(ExpectedConditions.elementToBeClickable(showBtn));
                driver.findElement(showBtn).click();
            }
        }
        catch(Exception e) {}
    }

    private void checkAllElementPrice(List<WebElement> items, int from, int to){
        for (WebElement good : items) {
            int price = getPriceFromString(good.findElement(By.className("_1u3j_pk1db")).getText());
            assert(from <= price && price <= to);
        }
    }

    private void selectPenultimate(List<WebElement> items) {
        By btnID = By.className("_4qhIn2-ESi");
        WebElement neededBrush = items.get(items.size() - 2);
        neededBrush.findElement(btnID).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("В корзине")));
    }

    private void goToBasket() {
        driver.findElement(By.className("header2-menu")).click();
    }

    private void makePriceGreaterThen(int x) {
        WebElement plusMinusBtn = driver.findElement(By.className("VcZj0jcCdD"));
        List<WebElement> btn = plusMinusBtn.findElements(By.className("_2w0qPDYwej"));
        while (getPriceFromString(driver.findElement(By.className("_1u3j_pk1db")).getText()) < x){
            btn.get(1).click();
        }
    }

    @Test
    private void thirdTest() {
        int minPrice = 999, maxPrice = 1999, threshold = 2999;
        //getBeruRu();
        //login();
        //selectCategory("Красота и гигиена", "Электрические зубные щетки");
        driver.get("https://beru.ru/catalog/elektricheskie-zubnye-shchetki/80961/list?hid=278374&pricefrom=1429&priceto=1429");
        //setRange(minPrice, maxPrice);
        openAllList();
        List<WebElement> searchResult = driver.findElements(By.className("grid-snippet"));
        checkAllElementPrice(searchResult, minPrice, maxPrice);
        selectPenultimate(searchResult);

        goToBasket();
        checkPrice();
        makePriceGreaterThen(threshold);

        wait.until(ExpectedConditions.textMatches(By.className("_2bi8z0hv1H"), Pattern.compile(".*бесплатно.*")));

        List<WebElement> orderInfo2 = driver.findElements(By.className("_1Q9ASvPbPN"));
        System.out.println(orderInfo2.get(1).getText().split("\n")[1]);
        checkPrice();

    }

}
