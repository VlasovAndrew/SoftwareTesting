import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
public class BasketPage extends BasePage {

    @FindBy(className = "_1Q9ASvPbPN")
    private List<WebElement> orderInfo;

    @FindBy(className = "_2bi8z0hv1H")
    private WebElement orderWindow;

    private ArrayList<Integer> value;

    private int getCoastFirstProduct(){
        return getPriceFromString(driver.findElement(By.className("_1u3j_pk1db")).getText());
    }

    private void splitPriceInfo(){
        value = new ArrayList<Integer>();
        for (WebElement raw: orderInfo) {
            String[] items = raw.getText().split("\n");
            String t = items[0], v = items[1];
            if (t.contains("Скидка")){
                v = "-" + v;
            }
            value.add(getPriceFromString(v));
        }
    }

    public BasketPage(EventFiringWebDriver driver){
        super(driver);
        this.splitPriceInfo();
    }

    @Step(value = "Получение стоимости товаров")
    public int getGoodsPrice(){
        return value.get(0);
    }

    @Step(value = "Получение значения скидки")
    public int getDiscount(){
        return (value.size() == 4 ? value.get(2) : 0);
    }

    @Step(value = "Получение значения доставки")
    public int getDeliveryPrice(){
        return value.get(1);
    }

    @Step(value = "Получение значения суммы всего заказа")
    public int getTotalPrice(){
        return value.get(value.size() - 1);
    }

    @Step(value = "Очистка корзины")
    public BasketPage clearBasket(){
        List<WebElement> deleteIcons = driver.findElements(By.cssSelector("div._1jPA8HAt83._3c1Ff1irZC"));
        for (WebElement icon: deleteIcons) {
            icon.click();
        }
        waitElementStaleness(orderWindow);
        return new BasketPage(driver);
    }

    @Step(value = "Увеличение стоимости товара до бесплатной даставки")
    public BasketPage makeProductPriceMoreThenThreshold(int threshold){
        while(true){
            WebElement plusMinusBtn = driver.findElement(By.className("VcZj0jcCdD"));
            WebElement btnPlus = plusMinusBtn.findElements(By.className("_2w0qPDYwej")).get(1);
            btnPlus.click();
            int currentPrice = getCoastFirstProduct();
            if (currentPrice > threshold){
                break;
            }
        }
        waitElementStaleness(orderWindow);
        return new BasketPage(driver);
    }
    @Step(value = "Проверка корректности в заказе")
    public void checkPriceInOrder(){
        int totalPrice   = getTotalPrice();
        int productPrice = getGoodsPrice();
        int discount     = getDiscount();
        int delivery     = getDeliveryPrice();
        assertThat(productPrice + discount + delivery).isEqualTo(totalPrice);
    }
}
