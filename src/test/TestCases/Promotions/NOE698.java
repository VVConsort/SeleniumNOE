package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import View.Footer.FooterView;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE698 extends BaseTest {

    @Parameters({"couponCode", "productCode"})
    @Test(description = "Fidélité : Passer un bon d'achat Fidélité A déjà utilisé sur la caisse OpenBravo et voir son prix rester identique")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-698")
    public void noe698(String couponCode, String productCode) throws MalformedURLException, InterruptedException {
        // Log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout produit
        ScanStep.scanValue(productCode, driver);
        // On stock le total à payer avant tentative d'application bon d'achat
        String totalToPayBeforeVoucher = new FooterView(driver).getTotalToPayElem().getText();
        // Tentative d'ajout de bon d'achat
        PaymentStepValue payStep = getNewPaymentStepValue(false);
        payStep.paymentId = couponCode;
        // On vérifie que le voucher ne ce soit pas appliqué
        payStep.expectedValue = false;
        PaymentStep.useVoucher(payStep);
        // Vérifier que le montant à payer n'a pas gébou
        BaseStepValue baseStep = getNewBaseStepValue(false);
        baseStep.expectedValue = totalToPayBeforeVoucher;
        TicketStep.checkTotalToPay(baseStep);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
