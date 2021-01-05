package Step.Value;

import Enums.PaymentMean;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class PaymentStepValue extends BaseStepValue{
    //  TODO Montant à payer, si vide = tout payer
    public String paymentAmount;
    // Moyen de paiement à utiliser
    public PaymentMean paymentMean;
    // Numéro moyen de paiement (num avoir, carte cadeau etc )
    public String paymentId;

    public PaymentStepValue(Object expectedValue,String paymentAmount,PaymentMean paymentMean ,String paymentId, WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(expectedValue, driver, soft, isHardAssert);
        this.paymentAmount = paymentAmount;
        this.paymentMean = paymentMean;
        this.paymentId = paymentId;
    }
}
