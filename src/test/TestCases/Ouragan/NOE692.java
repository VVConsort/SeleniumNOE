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

public class NOE692 extends BaseTest {

    @Parameters({"jsonFilePath", "expectedPendingAmount"})
    @Test(description = "Part : Récupérer une commande Click and Collect")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-692")
    public void noe692(String jsonFilePath, String expectedPendingAmount) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT intégré
        OuraganStep.openWorkOrder(documentCode, driver);
        BaseStepValue stepValue = getNewBaseStepValue(false);
        // Vérification du montant à payer
        stepValue.expectedValue = expectedPendingAmount;
        TicketStep.checkTotalToPay(stepValue);
        // Vérification qu'il n'y a plus rien à payer
        TicketStep.checkOrderAlreadyPaid(stepValue);
        // TODO Attendre accès base OB : vérification retour vers Ouragan
        // Vidage du BT
        TicketStep.deleteWorkOrder(driver);
    }
}
