package Step.Value.Payment;

import Enums.PaymentMean;
import Enums.SendTicketMode;
import Step.Value.BaseStepValue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class PaymentStepValue extends BaseStepValue {
    // Moyen de paiement à utiliser
    public PaymentMean paymentMean;
    // Numéro moyen de paiement (num avoir, carte cadeau etc )
    public String paymentId;
    // Méthode d'envoi du ticket
    public SendTicketMode sendTicketMode;

    public PaymentStepValue(ChromeDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(driver, soft, isHardAssert);
    }
}
