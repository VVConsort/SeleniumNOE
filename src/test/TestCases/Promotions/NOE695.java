package TestCases.Promotions;

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

public class NOE695 extends BaseTest {

    @Parameters({"productCode", "voucherCode", "expectedTotalToPay"})
    @Test(description = "Passer un bon d'achat Fidélité A sur la caisse OpenBravo et voir son prix diminuer")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-695")
    public void noe695(String productCode, String voucherCode, String expectedTotalToPay) throws MalformedURLException, InterruptedException {
        // Log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout produit
        //2158612
        ScanStep.scanValue(productCode, driver);
        // Ajout du bon d'achat
        // D58D21D605E47F63D8A0
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.paymentId = voucherCode;
        // On s'attend à ce que le bon d'achat soit ajouté au ticket
        payStep.expectedValue = true;
        PaymentStep.useVoucher(payStep);
        // Controle du prix à payer
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotalToPay;
        TicketStep.checkTotalToPay(stepValue);
        // On retire le bon d'achat du ticket afin de ne pas le griller
        PaymentStep.removeVoucher(payStep);
        // Fermeture du ticket
        TicketStep.deleteTicket(driver);
    }
}
