import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest extends BeruWork {
    @Test
    private void firstTest() {
        this.goOnBeruRu();
        this.login();
        WebElement profileButton = driver.findElement(this.loginButton);
        Assert.assertEquals(profileButton.getText(), "Мой профиль");
        this.logout();
    }
}
