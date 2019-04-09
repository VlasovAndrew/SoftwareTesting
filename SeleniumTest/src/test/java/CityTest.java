import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CityTest extends BeruWork {

    private By cityLinkID = By.className("link__inner");
    @Parameters({ "city" })
    @Test
    private void secondTest(String city) {
        this.goOnBeruRu();
        this.clickCityLink();
        this.inputDataIntoForm(city);
        Assert.assertEquals(getCurrentCity(), city);
        this.login();
        this.goToSettings();
        String settingCity = this.getSettingCity();
        Assert.assertEquals(getCurrentCity(), settingCity);
        this.logout();
    }

    @Step
    private void clickCityLink(){
        driver.findElement(cityLinkID).click();
    }

    @Step
    private void inputDataIntoForm(String city){
        WebElement popupForm = driver.findElement(By.className("header2-region-popup"));
        popupForm.findElement(By.className("input__control")).sendKeys(city);
        popupForm.findElement(By.className("region-suggest__list-item")).click();
        popupForm.findElement(By.className("button2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityLinkID));
    }

    @Step
    private void goToSettings(){
        driver.findElement(this.loginButton).click();
        driver.findElement(By.className("header2-user-menu__item_type_settings")).click();
    }

    private String getSettingCity(){
        WebElement settingsForm = driver.findElement(By.className("settings-list__title"));
        return settingsForm.findElement(cityLinkID).getText();
    }

    private String getCurrentCity(){
        WebElement cityLink = driver.findElement(cityLinkID);
        return cityLink.getText();
    }


}
