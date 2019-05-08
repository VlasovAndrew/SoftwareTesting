import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.ArrayList;
import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(className = "_1Q9ASvPbPN")
    private List<WebElement> orderInfo;

    @FindBy(className = "VcZj0jcCdD")
    private WebElement plusMinusBtn;

    @FindBy(className = "_1u3j_pk1db")
    private WebElement priceRow;

    @FindBy(className = "_2bi8z0hv1H")
    private WebElement orderWindow;

    private ArrayList<Integer> value;

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

    public int getGoodsPrice(){
        return value.get(0);
    }

    public int getDiscount(){
        return (value.size() == 4 ? value.get(2) : 0);
    }

    public int getDeliveryPrice(){
        return value.get(1);
    }

    public int getTotalPrice(){
        return value.get(value.size() - 1);
    }

    public int getCoastFirstProduct(){
        return getPriceFromString(priceRow.getText());
    }

    public BasketPage increaseFirstProduct(){
        WebElement btnPlus = plusMinusBtn.findElements(By.className("_2w0qPDYwej")).get(1);
        btnPlus.click();
        waitElementStaleness(orderWindow);
        return new BasketPage(driver);
    }

    public BasketPage clearBasket(){
        List<WebElement> deleteIcons = driver.findElements(By.cssSelector("div._1jPA8HAt83._3c1Ff1irZC"));
        for (WebElement icon: deleteIcons) {
            icon.click();
        }
        waitElementStaleness(orderWindow);
        return new BasketPage(driver);
    }
}
