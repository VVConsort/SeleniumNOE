package TestCases.PosOpening;

import Helpers.Test.BaseTest;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.LoggingStep;
import Step.PosOpeningStep;
import Step.Value.LoggingStepValue;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

// TODO PosOpeningValue, faire classe commune avec Logging ?
public class PosOpening extends BaseTest {

    @Parameters({"terminalKey", "profileName"})
    @Test(description = "Ouvre la caisse {terminalKey}")
    public void openPos(@Optional String terminalKey, @Optional String profileName) throws MalformedURLException, InterruptedException {
        // Si une caisse est spécifiée
        if (terminalKey != null && !terminalKey.isEmpty() && profileName != null && !profileName.isEmpty()) {
            // Ouvre OB et se log avec la caisse passée en paramètre
            LoggingStepValue logStepValue = new LoggingStepValue(TestSuiteProperties.OB_POS_URL, terminalKey, TestSuiteProperties.CHROME_PROFILE_PATH, profileName, TestSuiteProperties.USERNAME, TestSuiteProperties.PASSWORD);
            driver = LoggingStep.launchAndLogToOpenBravo(logStepValue);
        }
        // Sinon ouverture en utilisant les paramètres de la TestSuite
        else {
            driver = LoggingStep.launchAndLogToOpenBravo();
        }
        // Fermeture de la caisse
        PosOpeningStep.closePos(driver);
        // Ouverture de caisse
        PosOpeningStep.openPos(driver);
    }

}
