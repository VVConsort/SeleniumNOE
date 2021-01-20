package TestCases.Ouragan;

import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.OuraganStep;
import Step.ScanStep;
import io.qameta.allure.Link;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class NOE800 extends BaseTest {

    @Parameters({"jsonFilePath"})
    @Test(description = "Remise immédiate : Montage des pneus offert pour l'achat de 2 pneus")
    @Link(name = "Jira ticket", url = "https://openbravo.atlassian.net/browse/NOE-800")
    public void noe800(String jsonFilePath) throws IOException, InterruptedException {
        // Envoie du relevé atelier vers OB
        //String documentCode = OuraganStep.postWorkOrderToOpenBravo(jsonFilePath);
        // Lancement et log sur OB
        currentDriver = LoggingStep.launchAndLogOB();
        // Ouverture du BT intégré
        ScanStep.scanValue("0003001455771320881",currentDriver);
        System.out.println("002280016286111023517");
    }
}
