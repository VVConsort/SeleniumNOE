package TestCases.Promotions;

import Enums.Payment.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE682 extends BaseTest {

    @Parameters({"productCode", "voucherCode", "expectedTotal"})
    @Test(description = "Bon Achat : Génération d'un bon d'achat de 10€ en caisse si le total du ticket est supérieur à 50€")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-682")
    public void noe682(String productCode, String voucherCode, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout du produit
        ScanStep.scanValue(productCode, driver);
        // Ajout du bon d'achat
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.paymentId = voucherCode;
        payStepValue.paymentMean = PaymentMean.VOUCHER;
        PaymentStep.useVoucher(payStepValue);
        // Controle du prix du ticket
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
