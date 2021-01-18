package Step.Value.Payment;

import Enums.SendTicketMode;
import Step.Value.BaseStepValue;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class FinalizePaymentStepValue extends BaseStepValue {
    public SendTicketMode sendTicketMode;

    public FinalizePaymentStepValue(SendTicketMode sendTicketMode, Object expectedValue, WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(expectedValue, driver, soft, isHardAssert);
        this.sendTicketMode = sendTicketMode;
    }
}
