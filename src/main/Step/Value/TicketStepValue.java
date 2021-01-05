package Step.Value;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class TicketStepValue extends BaseStepValue{

    public TicketStepValue(Object expectedValue, WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(expectedValue, driver, soft, isHardAssert);
    }
}
