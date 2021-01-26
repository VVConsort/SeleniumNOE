package TestCases.Promotions;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE763 extends BaseTest {

    @Parameters({"productCode", "expectedTotalAmount",})
    @Test(description = "Remise immédiate : remise sur montant ticket")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-763")
    public void noe763(String productCode, String expectedTotalAmount) throws IOException, InterruptedException {
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Ajout du produit générique
        ScanStep.scanValue(productCode, currentDriver, 3);
        // Vérifie le montant à payer
        BaseStepValue ticketStepVal = getNewBaseStepValue(false);
        ticketStepVal.expectedValue = expectedTotalAmount;
        TicketStep.checkTotalToPay(ticketStepVal);
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
    }

}
