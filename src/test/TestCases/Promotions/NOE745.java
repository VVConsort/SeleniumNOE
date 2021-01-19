package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE745 extends BaseTest {

    @Parameters({"productCode", "expectedDiscountAmount", "discountLabel", "expectedTotal"})
    @Test(description = "Acheter 2 produits A, le troisième produit B offert")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-745")
    public void noe745(String productCode, String expectedDiscountAmount, String discountLabel, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan 2 fois le produit
        ScanStep.scanValue(productCode, currentDriver, 2);
        // On vérifie l'application d'une remise égale au montant du produit B
        DiscountStepValue discStepValue = getNewDiscountStepValue(false);
        discStepValue.expectedValue = expectedDiscountAmount;
        discStepValue.discountLabel = discountLabel;
        discStepValue.associatedProduct = productCode;
        DiscountStep.checkDiscountLineAmount(discStepValue);
        // Vérification du prix total
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Suppression du ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
