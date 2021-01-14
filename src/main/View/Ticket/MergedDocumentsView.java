package View.Ticket;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Pop up des documents associés
 */
public class MergedDocumentsView extends BaseView {

    // Xpath du bouton "Fermer document associés"
    private static final String CANCEL_BTN_XPATH="//*[@id=\\\"terminal_containerWindow_pointOfSale_OBMTR_UI_ModalOpenMergeRelatedReceipts_footer_openMergeRelatedReceipts_btnCancel\\\"]\"";

    public MergedDocumentsView(WebDriver driver)
    {
        init(driver,this);
    }

    /**
     * Appuie sur le bouton annuler  si il existe
     */
    public void clickCancelButton()
    {
        super.searchAndClickElement(CANCEL_BTN_XPATH);
    }
}
