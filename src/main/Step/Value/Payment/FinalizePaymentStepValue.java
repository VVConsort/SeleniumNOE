package Step.Value.Payment;

import Enums.SendTicketMode;
import Step.Value.BaseStepValue;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class FinalizePaymentStepValue extends BaseStepValue {
    // Méthode d'envoi du ticket
    public SendTicketMode sendTicketMode;

    public FinalizePaymentStepValue(WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(driver, soft, isHardAssert);
    }
}
