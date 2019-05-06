import io.qameta.allure.listener.LifecycleListener;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;


public class StepWithAttachments implements StepLifecycleListener{

    /*@Override
    public void beforeStepStop(final StepResult result) {
        System.out.println("Before Step Finish");
        ScreenShoter.attachScreenShot(BasePage.driver());
    }*/

    @Override
    public void afterStepStart(final StepResult result) {
        System.out.println("After Step Start");
        ScreenShoter.attachScreenShot(BasePage.driver());
    }



}