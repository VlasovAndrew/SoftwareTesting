import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CityTest extends BaseTest {

    private MainPage mp;
    private SettingPage sp;
    private String city;

    @Test(dataProvider = "city-data-provider", dataProviderClass = Options.class)
    @Description("Проверка корректности изменения города на главной странице и в настройках.")
    private void cityTest(String city){
        mp = new MainPage(this.driver);
        this.city = city;
        this.changeCity();
        this.checkCityAfterChanging();
        this.login();
        this.goToSettings();
        this.checkEqualityCityInSettings();
    }

    @Step
    private void changeCity(){
        mp = mp.setCity(city);
    }

    @Step
    private void checkCityAfterChanging(){
        assertThat(mp.getCityName()).isEqualToIgnoringCase(city);
    }

    @Step
    private void login(){
        mp = mp.login();
    }

    @Step
    private void goToSettings(){
        sp = mp.clickOnSettings();
    }

    @Step
    private void checkEqualityCityInSettings(){
        assertThat(sp.getCityInSettings()).isEqualToIgnoringCase(sp.getCityInTopPage());
    }
}
