import io.qameta.allure.listener.LifecycleListener;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class  StepWithAttachments extends TestListenerAdapter implements StepLifecycleListener  {

    @Override
    public void afterStepStart(final StepResult result) {
        ScreenShoter.attachScreenShot(BasePage.driver());
    }

    @Override
    public void beforeStepStop(final StepResult result) {
        ScreenShoter.attachScreenShot(BasePage.driver());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenShoter.attachScreenShot(BasePage.driver());
    }

}