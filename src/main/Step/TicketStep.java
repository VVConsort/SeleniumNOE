package Step;

import Helpers.Element.WaitHelper;
import Step.Value.BaseStepValue;
import View.Footer.FooterView;
import View.Ticket.MergedDocumentsView;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * Classe utilitaire pour les promotions
 */
public class TicketStep {

    // Temps de chargement du BT
    private static final int BT_LOADING_TIME =1500;

    /**
     * Vide le ticket en cours
     * @param driver
     */
    @Step("Vide le ticket")
    public static void deleteTicket(WebDriver driver) {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
        // Clic sur 'Confirmer suppression'
        footerView.clickOnConfirmDeleteBtn();
    }

    @Step("Vide le ticket")
    public static void deleteWorkOrder(WebDriver driver)
    {
        FooterView footerView = new FooterView(driver);
        // Clic sur 'Vider sur ticket'
        footerView.clickOnDeleteTicketBtn();
    }

    /**
     * Ferme la fenetre des documents associés
     * @param driver
     */
    @Step("Fermeture des documents associés")
    public static void closeMergedDocuments(WebDriver driver) throws InterruptedException {
        MergedDocumentsView mergedDoc = new MergedDocumentsView(driver);
        // Click sur 'annuler'
        mergedDoc.clickCancelButton();
        // Attente chargement cache
        WaitHelper.waitUntilLoadIsFinished(driver,10);
        // FIXME : L'affichage de BT se fait ligne par ligne, sans attente toutes les éléments ne sont pas chargés et les comparaisons de valeures deviennent fausses
        // A terme conditionner cette attente par la visiblité d'un élément
        Thread.sleep(BT_LOADING_TIME);
    }

    @Step("Vérifie que la valeur du ticket est égale à {stepValue.expectedValue}")
    public static void checkTotalToPay(BaseStepValue stepValue) {
        // Page panier
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        // Comparaison du total avec la valeure attendue
        stepValue.isEquals(receiptView.getTotalAmount());
    }
}
