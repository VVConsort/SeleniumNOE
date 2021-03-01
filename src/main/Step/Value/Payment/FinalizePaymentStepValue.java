package Step.Value.Payment;

import Enums.Payment.SendTicketMode;
import Step.Value.BaseStepValue;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class FinalizePaymentStepValue extends BaseStepValue {
    // Méthode d'envoi du ticket
    public SendTicketMode sendTicketMode;

    public FinalizePaymentStepValue(ChromeDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(driver, soft, isHardAssert);
    }
}
