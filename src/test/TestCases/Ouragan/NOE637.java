package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.TicketStep;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE637 extends BaseTest {

    @Parameters({"jsonFilePath"})
    @Test(description = "Scanner et régler l'acompte d'une Réservation Magasin contenant 1 article avec 1 acompte de 30€ et 1 article à 20€")
    @Link(name = "Jira ticket", url = "https://mobivia.atlassian.net/browse/NOE-637")
    public void noe637(String jsonFilePath) throws IOException, InterruptedException {
        // Intégration du BT
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // Vidage du ticket
        TicketStep.deleteTicket(driver);
        // Ouverture du BT Ouragan
        OuraganStep.openWorkOrder(documentCode, driver);

    }
}
