package View.Ticket;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Pop up des documents associés
 */
public class MergedDocumentsView extends BaseView {

    // Xpath du bouton "Fermer document associés"
    private static final String CANCEL_BTN_XPATH = "//*[@id=\\\"terminal_containerWindow_pointOfSale_OBMTR_UI_ModalOpenMergeRelatedReceipts_footer_openMergeRelatedReceipts_btnCancel\\\"]\"";
    // Bouton "Fermer documents associés"
    private WebElement cancelBtn;

    public MergedDocumentsView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur le bouton annuler si il existe
     */
    public void clickCancelButton() {
        // Si l'élément est déjà chargé
        if (cancelBtn != null) {
            super.click(cancelBtn, false);
        } else {
            findAndClickElement(CANCEL_BTN_XPATH, true);
        }
    }

    /**
     * Renvoi vrai si la fenetre est affichée
     * @return
     */
    public boolean isPresent() {
        cancelBtn = findElement(CANCEL_BTN_XPATH, false);
        return cancelBtn != null;
    }
}
