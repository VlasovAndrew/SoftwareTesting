import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

public class BrushTest extends BaseTest {

    private MainPage mp;
    private ProductPage pp;
    private BasketPage bp;

    private int from = 999;
    private int to = 1999;
    private int threshold = 2999;

    @Test
    @Description("Покупка электрической зубной счетки. " +
                 "Ввод интервала стоимости и проверка цена всех найденных товаров." +
                 "Проверка бесплатной доставки после увеличения стоимости заказа.")
    private void brushTest(){
        mp = new MainPage(this.driver);
        this.login();
        this.goToElectricToothBrush();
        this.setPriceRange();
        this.checkAllPrice();
        this.buyBrush();
        this.goToBasket();
        this.checkPriceInOrder();
        this.makeProductPriceMoreThenThreshold();
        this.checkFreeDelivery();
        this.checkPriceInOrder();
        this.clearBasket();
    }

    @Step
    private void login(){
        mp = mp.login();
    }

    @Step
    private void goToElectricToothBrush(){
        pp = mp.goToCategory("Красота и гигиена"
                            , "Электрические зубные щетки");
    }

    @Step
    private void setPriceRange(){
        pp = pp.setRange(from, to);
    }

    @Step
    private void checkAllPrice(){
        ArrayList<Integer> productPrice = pp.getProductPrice();
        for (Integer value: productPrice){
            assertThat(value <= to && value >= from).isTrue();
        }
    }

    @Step
    private void buyBrush(){
        pp = pp.buyPenultimate();
    }

    @Step
    private void goToBasket(){
        bp = pp.goToBasket();
    }

    @Step
    private void checkPriceInOrder(){
        int totalPrice   = bp.getTotalPrice();
        int productPrice = bp.getGoodsPrice();
        int discount     = bp.getDiscount();
        int delivery     = bp.getDeliveryPrice();
        assertThat(productPrice + discount + delivery).isEqualTo(totalPrice);
    }

    @Step
    private void makeProductPriceMoreThenThreshold(){
        while(true){
            int currentPrice = bp.getCoastFirstProduct();
            if (currentPrice > threshold){
                break;
            }
            bp = bp.increaseFirstProduct();
        }
    }

    @Step
    private void checkFreeDelivery(){
        int delivery = bp.getDeliveryPrice();
        assertThat(delivery).isEqualTo(0);
    }

    @Step
    private void clearBasket(){
        bp = bp.clearBasket();
    }
}
