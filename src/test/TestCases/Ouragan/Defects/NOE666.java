package TestCases.Ouragan.Defects;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.TicketStep;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE666 extends BaseTest {

    @Test(description = "azaza")
    @Parameters({"jsonFilePath"})
    public void noe666(String jsonFilePath) throws IOException, InterruptedException {
        // Lancement d'OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        String documentCode = "";
        // On va intégrer et scanner des documents tant qu'une exception n'est pas levée
        for (int i = 1; i < 100; i++) {

            System.out.println("Scan numéro : " + i);
            // Envoie REST
            documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
            // Scan et ouverture du document sur OB
            OuraganStep.openWorkOrder(documentCode, driver);
            TicketStep.deleteTicket(driver);
        }
    }
}
