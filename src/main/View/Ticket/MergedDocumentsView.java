package View.Ticket;

import View.BaseView;
import org.openqa.selenium.WebDriver;

/**
 * Pop up des documents associés
 */
public class MergedDocumentsView extends BaseView {

    // Xpath du bouton "Fermer document associés"
    private static final String CANCEL_BTN_XPATH = "//*[@id=\\\"terminal_containerWindow_pointOfSale_OBMTR_UI_ModalOpenMergeRelatedReceipts_footer_openMergeRelatedReceipts_btnCancel\\\"]\"";

    public MergedDocumentsView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur le bouton annuler si il existe
     */
    public void clickCancelButton() {
        super.searchAndClickElement(CANCEL_BTN_XPATH);
    }

    /**
     * Renvoi vrai si la fenetre est affichée
     * @return
     */
    public boolean isPresent() {
        return super.isElementPresentOnView(CANCEL_BTN_XPATH);
    }
}
