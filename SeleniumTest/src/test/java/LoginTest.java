import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {
    private MainPage mp;

    @Test
    @Description("Проверка изменения надписи на кнопке после входа в аккаунт.")
    private void loginTest() {
        mp = new MainPage(driver);
        this.goIntoAccaunt();
        this.checkCorrectButton();
    }

    @Step
    private void goIntoAccaunt(){
        mp = mp.login();
    }

    @Step
    private void checkCorrectButton(){
        assertThat(this.mp.getLogitBtnText()).isEqualToIgnoringCase("Мой профиль");
    }

}
