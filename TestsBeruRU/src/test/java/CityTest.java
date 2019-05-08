import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CityTest extends BaseTest {
    @Test(dataProvider = "city-data-provider", dataProviderClass = Options.class
            , description = "Проверка корректности изменения города на главной странице и в настройках.")
    private void cityTest(String city) {
        MainPage mp = new MainPage(this.driver).setCity(city);
        assertThat(mp.getCityName()).isEqualToIgnoringCase(city);
        mp.login();
        SettingPage sp = mp.clickOnSettings();
        assertThat(sp.getCityInSettings()).isEqualToIgnoringCase(sp.getCityInTopPage());
    }
}
