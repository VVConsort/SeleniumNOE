package Step.Value;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class TicketStepValue extends BaseStepValue {

    public TicketStepValue(ChromeDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(driver, soft, isHardAssert);
    }
}
