package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE744 extends BaseTest {

    @Parameters({"productACode", "productBCode","expectedDiscountAmount","discountLabel","expectedTotal"})
    @Test(description = "Acheter 2 produits A, le troisième produit B offert")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-744")
    public void noe744(String productACode, String productBCode, String expectedDiscountAmount, String discountLabel, String expectedTotal) throws MalformedURLException, InterruptedException {
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Scan 2 fois le produit A
        ScanStep.scanValue(productACode, currentDriver, 2);
        // Scan le produit B
        ScanStep.scanValue(productBCode, currentDriver);
        // On vérifie l'application d'une remise égale au montant du produit B
        DiscountStep.checkDiscountLineAmount(newDiscountStepValue(expectedDiscountAmount, discountLabel, productBCode, false));
        // Vérification du prix total
        TicketStep.checkTotalToPay(newStepValue(expectedTotal, false));
        // Suppression du ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
