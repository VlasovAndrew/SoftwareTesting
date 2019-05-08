import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {
    @Test
    @Description("Проверка изменения надписи на кнопке после входа в аккаунт.")
    private void loginTest() {
        MainPage mp = new MainPage(driver).login();
        assertThat(mp.getLogitBtnText()).isEqualToIgnoringCase("Мой профиль");
    }
}
