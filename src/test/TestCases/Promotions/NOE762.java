package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.DiscountStep;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.DiscountStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class NOE762 extends BaseTest {

    @Parameters({"discountLabel", "discountedProductCode", "expectedDiscountAmount",})
    @Test(description = "Remise immédiate : Remise en montant sur un article (cet article uniquement)")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-762")
    public void noe762(String discountLabel, String discountedProductCode, String expectedDiscountAmount) throws MalformedURLException, InterruptedException {
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout du produit
        ScanStep.scanValue(discountedProductCode, driver);
        // Control du montant de la promotion
        DiscountStepValue discStepVal = getNewDiscountStepValue(false);
        discStepVal.expectedValue = expectedDiscountAmount;
        discStepVal.discountLabel = discountLabel;
        discStepVal.associatedProduct = discountedProductCode;
        DiscountStep.checkDiscountLineAmount(discStepVal);
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
    }
}
