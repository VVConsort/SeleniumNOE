package TestCases.Promotions;

import Helpers.Element.WaitHelper;
import Helpers.Ouragan.OuraganOrderLoaderHelper;
import Step.ScanStep;
import Step.LoggingStep;
import Step.DiscountStep;
import Step.TicketStep;
import Helpers.Test.BaseTest;

import io.qameta.allure.Step;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Step.OuraganStep;

import java.io.IOException;
import java.net.MalformedURLException;

public class NOE774 extends BaseTest {

    @Parameters({"jsonFilePath"})
    @Test(description = "Remise immédiate : Montage des pneus offert pour l'achat de 2 pneus")
    public void noe774(String jsonFilePath,String expectedDiscountAmount,String discountLabel, String expectedTotal) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // On vide le ticket
        TicketStep.deleteTicket(currentDriver);
        // Scan du BT intégré
        ScanStep.scanValue(documentCode,currentDriver);
        // On ferme la fenetre des documents associés
        TicketStep.closeMergedDocuments(currentDriver);
        // On attend le chargement
        WaitHelper.waitUntilLoadIsFinished(currentDriver,20);
        // Controle de la gratuité du montage
        DiscountStep.checkDiscountLineAmount( newDiscountStepValue(expectedDiscountAmount,discountLabel,null,false));
        // Controle du montant total
        TicketStep.checkTotalToPay( newStepValue(expectedTotal,false));
        // Vidage du ticket
        TicketStep.deleteWorkOrder(currentDriver);

    }


}
