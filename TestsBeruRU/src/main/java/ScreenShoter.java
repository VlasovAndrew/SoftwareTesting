import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.openqa.selenium.*;

public class ScreenShoter {

    private static void attach(byte[] data){
        // attaching screenshot
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.addAttachment("Screenshot", "image/png", "png", data);
    }

    public static void attachScreenShot(WebDriver driver){
        byte[] data = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        attach(data);
    }
}
