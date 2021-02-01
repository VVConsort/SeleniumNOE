package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE553 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedTotal"})
    @Test(description = "Scanner un RA contenant un article A et 1 article B x 2")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-553")
    public void noe553(String jsonFilePath, String expectedTotal) throws IOException, InterruptedException {
        // Intégration du BT
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Scan du BT
        OuraganStep.openWorkOrder(documentCode, driver);
        // Vérification du total
        BaseStepValue stepValue = getNewBaseStepValue(false);
        stepValue.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(stepValue);
        // Vidage
        TicketStep.deleteWorkOrder(driver);
    }
}
