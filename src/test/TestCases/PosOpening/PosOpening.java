package TestCases.PosOpening;

import Helpers.Test.BaseTest;
import Helpers.Test.TestSuiteProperties.TestSuiteProperties;
import Step.LoggingStep;
import Step.PosOpeningStep;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

// TODO PosOpeningValue, faire classe commune avec Logging ?
public class PosOpening extends BaseTest {

    @Parameters({"terminalKey", "profileName"})
    @Test(description = "Ouvre la caisse {terminalKey}")
    public void openPos(String terminalKey, String profileName) throws MalformedURLException {
        // Ouvre OB et se log avec la caisse passée en paramètre
        LoggingStep.launchAndLogOB(newLoggingStepValue(TestSuiteProperties.OB_POS_URL, terminalKey, TestSuiteProperties.CHROME_PROFILE_PATH, profileName, TestSuiteProperties.USERNAME, TestSuiteProperties.PASSWORD));
        currentDriver = LoggingStep.launchAndLogOB();
        // Ouverture de la caisse
        PosOpeningStep.openPos(currentDriver);
    }

}
