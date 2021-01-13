package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.ScanStep;
import Step.TicketStep;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE692 extends BaseTest {

    @Parameters({"jsonFilePath","expectedPendingAmount"})
    @Test(description = "Part : Récupérer une commande Click and Collect")
    public void noe692(String jsonFilePath, String expectedPendingAmount) throws IOException, InterruptedException {
        System.setProperty("https.proxyHost", "Panoz4.nor.lan");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("https.proxyUser", "vvanhaute");
        System.setProperty("https.proxyPassword", "SCEpter1986");
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
        // Vérification du montant à payer
        TicketStep.checkTotalToPay(newStepValue(expectedPendingAmount,false));
        // Vidage du BT
        TicketStep.deleteWorkOrder(currentDriver);
    }
}
