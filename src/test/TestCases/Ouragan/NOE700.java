package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.*;
import Step.Value.DiscountStepValue;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE700 extends BaseTest {

    @Parameters({"jsonFilePath","discountLabel","discountAmount","expectedTotal"})
    @Test(description = "Scanner un RA contenant une remise manuelle")
    public void noe700(String jsonFilePath, String discountLabel, String discountAmount, String expectedTotal) throws IOException, InterruptedException {
        System.setProperty("java.net.useSystemProxies","true");
        /*System.setProperty("https.proxyHost", "Panoz4.nor.lan");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("https.proxyUser", "vvanhaute");
        System.setProperty("https.proxyPassword", "SCEpter1986");*/
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
        // Données à controler
        DiscountStepValue discStepValue = newDiscountStepValue(discountAmount, discountLabel, null, false);
        // Controle du montant de la promo
        DiscountStep.checkDiscountLineAmount(discStepValue);
        // Controle du montant du ticket
        TicketStep.checkTotalToPay(newStepValue(expectedTotal, false));
        // Vidage du ticket
        TicketStep.deleteWorkOrder(currentDriver);
    }
}
