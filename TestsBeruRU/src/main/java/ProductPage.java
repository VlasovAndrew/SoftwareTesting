import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends MainPage {

    @FindBy(name = "Цена от")
    private WebElement priceFieldFrom;

    @FindBy(name = "Цена до")
    private WebElement priceFieldTo;

    @FindBy(className = "n-snippet-list")
    private WebElement productWall;

    @FindBy(className = "grid-snippet")
    private List<WebElement> products;

    @Step(value = "Открытие всего списка товаров")
    public void openAllList(){
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

    public ProductPage(EventFiringWebDriver driver){
        super(driver);
    }

    @Step(value = "Установка диапозона стоимости товаров")
    public ProductPage setRange(int from, int to){
        priceFieldFrom.sendKeys(String.valueOf(from));
        priceFieldTo.sendKeys(String.valueOf(to));
        waitElementStaleness(productWall);
        this.openAllList();
        return new ProductPage(driver);
    }

    @Step(value = "Получение стоимостей товаров в списке")
    public ArrayList<Integer> getProductPrice(){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (WebElement product : products) {
            int price = getPriceFromString(product.findElement(By.className("_1u3j_pk1db")).getText());
            result.add(price);
        }
        return result;
    }

    @Step(value = "Покупка предпоследнего товара в списке")
    public ProductPage buyPenultimate(){
        By btnID = By.className("_4qhIn2-ESi");
        WebElement product = products.get(products.size() - 2);
        product.findElement(btnID).click();
        product.findElement(By.linkText("В корзине"));
        return new ProductPage(driver);
    }
}
