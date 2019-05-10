import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BrushTest extends BaseTest {

    private MainPage mp;
    private ProductPage pp;
    private BasketPage bp;

    private int from = 999;
    private int to = 1999;
    private int threshold = 2999;

    @Test(description = "Покупка электрической зубной счетки. " +
            "Ввод интервала стоимости и проверка цена всех найденных товаров." +
            "Проверка бесплатной доставки после увеличения стоимости заказа.")
    private void brushTest(){
        mp = new MainPage(this.driver).login();
        pp = mp.goToCategory("Красота и гигиена", "Электрические зубные щетки").setRange(from, to);

        for (Integer value: pp.getProductPrice()){
            assertThat(value <= to && value >= from).isTrue();
        }

        pp = pp.buyPenultimate();
        bp = pp.goToBasket();
        bp.checkPriceInOrder();
        bp = bp.makeProductPriceMoreThenThreshold(threshold);
        bp.checkPriceInOrder();
        assertThat(bp.getDeliveryPrice()).isEqualTo(0);
        bp = bp.clearBasket();
    }


}
