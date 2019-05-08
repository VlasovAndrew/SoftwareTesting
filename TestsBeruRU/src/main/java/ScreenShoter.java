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

    public static void attachScreenShot(WebElement element, WebDriver driver){
        // creating frame and scroll to element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);"
                        , element, "border: 5px solid red;");
        js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});"
                        , element);
        // creating screenshot
        byte[] data = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        // deletion frame
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
        attach(data);
    }
}
