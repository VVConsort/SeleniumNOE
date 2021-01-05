package Step;

import Step.Value.BaseStepValue;
import Pages.Footer.FooterView;
import Pages.Ticket.MergedDocumentsView;
import Pages.Ticket.ReceiptView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * Classe utilitaire pour les promotions
 */
public class TicketStep {

    /**
     *
     * @param ticket
     * @param discountLabel
     * @return
     */
    /*public static String getDiscountValue(Ticket ticket, String discountLabel)
    {
        // On récupère le montant de la promo
        return ticket.getDiscountLineAmount(discountLabel);
    }*/

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
    public static void closeMergedDocuments(WebDriver driver) {
        MergedDocumentsView mergedDoc = new MergedDocumentsView(driver);
        // Click sur 'annuler'
        mergedDoc.clickCancelButton();
    }

    @Step("Vérifie que la valeur du ticket est égale à {stepValue.expectedValue}")
    public static void checkTotalToPay(BaseStepValue stepValue) {
        // Page panier
        ReceiptView receiptView = new ReceiptView(stepValue.driver);
        // Comparaison du total avec la valeure attendue
        stepValue.isEquals(receiptView.getTotalAmount());
    }
}
