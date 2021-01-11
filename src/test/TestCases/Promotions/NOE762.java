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

public class NOE762 extends BaseTest {

    @Parameters({"discountLabel", "discountedProductCode", "expectedDiscountAmount",})
    @Test(description = "Remise immédiate : Remise en montant sur un article (cet article uniquement)")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-762")
    public void noe762(String discountLabel, String discountedProductCode, String expectedDiscountAmount) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du produit
        ScanStep.scanValue(discountedProductCode, currentDriver);
        // Control du montant de la promotion
        DiscountStep.checkDiscountLineAmount(newDiscountStepValue(expectedDiscountAmount, discountLabel, discountedProductCode, false));
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
    }
}
