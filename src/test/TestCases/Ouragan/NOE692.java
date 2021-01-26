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

    @Parameters({"jsonFilePath", "expectedPendingAmount"})
    @Test(description = "Part : Récupérer une commande Click and Collect")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-692")
    public void noe692(String jsonFilePath, String expectedPendingAmount) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(currentDriver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode,currentDriver);
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Vérification du montant à payer
        stepValue.expectedValue = expectedPendingAmount;
        TicketStep.checkTotalToPay(stepValue);
        // Vérification qu'il n'y a plus rien à payer
        TicketStep.checkOrderAlreadyPaid(stepValue);
        // TODO Attendre accès base OB
        // Vidage du BT
        TicketStep.deleteWorkOrder(currentDriver);
    }
}
