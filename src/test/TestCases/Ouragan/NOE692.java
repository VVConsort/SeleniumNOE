package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE692 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedPendingAmount","expectedState"})
    @Test(description = "Part : Récupérer une commande Click and Collect")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-692")
    public void noe692(String jsonFilePath, String expectedPendingAmount,String expectedState) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan du BT
        ScanStep.scanValue(documentCode, currentDriver);
        // Fermeture des documents associés
        TicketStep.closeMergedDocuments(currentDriver);
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Vérification de l'état
        /*stepValue.expectedValue = expectedState;
        TicketStep.checkOrderState(stepValue);*/
        // Vérification du montant à payer
        stepValue.expectedValue = expectedPendingAmount;
        TicketStep.checkTotalToPay(stepValue);
        // Vidage du BT
        TicketStep.deleteWorkOrder(currentDriver);
    }
}
