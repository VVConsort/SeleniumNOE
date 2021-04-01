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

public class NOE683 extends BaseTest {

    @Parameters({"productCode", "voucherCode", "expectedTotal"})
    @Test(description = "Passer un bon d'achat A (Non Fidélité) sur la caisse OpenBravo et émis depuis OpenBravo")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-683")
    public void noe683(String productCode, String voucherCode, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout du produit
        ScanStep.scanValue(productCode, driver);
        // Ajout du bon d'achat
        PaymentStepValue payStepValue = getNewPaymentStepValue(false);
        payStepValue.paymentMean = PaymentMean.VOUCHER;
        payStepValue.paymentId = voucherCode;
        PaymentStep.useVoucher(payStepValue);
        // Controle du prix du ticket
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // On retire le bon d'achat du ticket
        PaymentStep.removeVoucher(payStepValue);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
