package TestCases.PosOpening;

import Step.LoggingStep;
import Helpers.Element.WaitHelper;
import View.PosOpening;
import Helpers.Test.BaseTest;

import Helpers.Element.PopupHelper;

import io.qameta.allure.Step;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class CashOpening extends BaseTest {

    // Timeout d'ouverture du cache
    private static final int CACHE_LOADING_TIMEOUT_IN_SECONDS = 120;
    private PosOpening posOpenPage;

    @Test(description = "Ouvre la caisse en validant le montant monnaie")
    public void openPos() throws MalformedURLException {
        // Ouvre OB et se log
        logToOpenBravo();
        posOpenPage = new PosOpening(currentDriver);
        // Confirme la date d'ouverture du mag si nécessaire
        posOpenPage.confirmPosOpeningDate();
        // Entre en ouverture de caisse
        enterOpening();
        // Valide le montant monnaie
        validateCash();
        // Confirme le succes de l'ouverture
        confirmOpeningSucces();
        // Attend la fin de chargement du cache
        waitUntilCacheIsLoaded();
    }
    ////*[@id="terminal_confirmationContainer_dynamicConfirmationPopup_footer_continueLogin"]

    @Step("Log sur OpenBravo")
    private void logToOpenBravo() throws MalformedURLException {
        currentDriver = LoggingStep.launchAndLogOB();
    }

    @Step("Entre en ouverture de caisse")
    private void enterOpening() {
        //Valide la pop et entre en ouverture de caisse
        PopupHelper.clickOkBtn(currentDriver);
    }

    @Step("Valide le montant monnaie")
    private void validateCash() {
        // Page ouverture de caisse
        PosOpening posOpening = new PosOpening(currentDriver);
        // Clic bouton "Suivant"
        posOpening.clickNext();
        // Valide le détail monnaie
        //posOpening.clickValidateCash();
        // Appuie sur "Finaliser"
        posOpening.clickNext();
        // Appuie sur "Traiter imprimer"
        posOpening.clickNext();
    }

    @Step("Confirme le succès d'ouverture de caisse")
    private void confirmOpeningSucces() {
        // Confirme le succès de l'ouverture
        PopupHelper.clickOkBtn(currentDriver);
    }

    @Step("Attend que le cache soit chargé")
    private void waitUntilCacheIsLoaded() {
        WaitHelper.waitUntilLoadIsFinished(currentDriver, CACHE_LOADING_TIMEOUT_IN_SECONDS);
    }
}
