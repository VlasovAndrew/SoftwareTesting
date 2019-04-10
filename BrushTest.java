import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BrushTest extends BeruWork {


    @Parameters({"minPrice", "maxPrice", "threshold"})
    @Test
    private void thirdTest(int minPrice, int maxPrice, int threshold) {
        this.goOnBeruRu();
        this.login();
        this.selectCategory("Красота и гигиена", "Электрические зубные щетки");
        this.setRange(minPrice, maxPrice);
        this.openAllList();
        List<WebElement> searchResult = driver.findElements(By.className("grid-snippet"));
        this.checkAllElementPrice(searchResult, minPrice, maxPrice);
        this.selectPenultimate(searchResult);
        this.goToBasket();
        this.checkPrice();
        this.makePriceGreaterThen(threshold);
        this.checkFreeDeliver();
        this.checkPrice();
        this.clearBasket();
        this.logout();
    }

    @Step
    private void selectCategory(String mainCategory, String subcategory){
        driver.findElement(By.className("header2__navigation")).click();
        WebElement formCategory = driver.findElement(By.className("n-navigation-vertical-category"));
        WebElement element = formCategory.findElement(By.linkText(mainCategory));
        action.moveToElement(element).perform();
        WebElement formMenu = driver.findElement(By.className("n-navigation-vertical-menu"));
        formMenu.findElement(By.linkText(subcategory)).click();
    }

    @Step
    private void setRange(int from, int to){
        driver.findElement(By.name("Цена от")).sendKeys(String.valueOf(from));
        driver.findElement(By.name("Цена до")).sendKeys(String.valueOf(to));
        this.waitPageLoad();
    }

    @Step
    private void openAllList(){
        try {
            int stopNumber = 1000000;
            for (int i = 0; i < stopNumber; i++) {
                By showBtn = By.linkText("ПОКАЗАТЬ ЕЩЁ");
                wait.until(ExpectedConditions.elementToBeClickable(showBtn));
                driver.findElement(showBtn).click();
            }
        }
        catch(Exception e) {}
    }

    @Step
    private void goToBasket() {
        driver.findElement(By.className("header2-menu")).click();
    }

    private void checkAllElementPrice(List<WebElement> items, int from, int to) {
        for (WebElement product : items) {
            int price = getPriceFromString(product.findElement(By.className("_1u3j_pk1db")).getText());
            assert(from <= price && price <= to);
        }
    }

    private void selectPenultimate(List<WebElement> items) {
        By btnID = By.className("_4qhIn2-ESi");
        WebElement neededBrush = items.get(items.size() - 2);
        neededBrush.findElement(btnID).click();
        ExpectedConditions.elementToBeClickable(By.linkText("В корзине"));
    }


    private int getPriceFromString(String text){
        String number = text.replaceAll("[^0-9\\-]", "");
        if (number.length() == 0){
            return 0;
        }
        return Integer.parseInt(number);
    }

    @Step
    private void checkPrice(){
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

    @Step
    private void makePriceGreaterThen(int x) {
        WebElement plusMinusBtn = driver.findElement(By.className("VcZj0jcCdD"));
        WebElement btnPlus = plusMinusBtn.findElements(By.className("_2w0qPDYwej")).get(1);
        By priceWindow = By.className("_1u3j_pk1db");
        while (getPriceFromString(driver.findElement(priceWindow).getText()) < x){
            btnPlus.click();
            this.waitPageLoad();
        }
    }

    @Step
    private void checkFreeDeliver(){
        WebElement headerOrder = driver.findElement(By.className("_3BLMSktvAP"));
        String text = headerOrder.getText();
        Assert.assertEquals(text, "Поздравляем! Вы получили бесплатную доставку на ваш заказ");
    }

    private void clearBasket(){
        driver.findElement(By.className("_1jPA8HAt83 ")).click();
    }
}
