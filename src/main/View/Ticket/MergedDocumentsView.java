package View.Ticket;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Pop up des documents associés
 */
public class MergedDocumentsView extends BaseView {

    // Bouton cancel
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_OBMTR_UI_ModalOpenMergeRelatedReceipts_footer_openMergeRelatedReceipts_btnCancel\"]")
    private WebElement cancelBtn;

    public MergedDocumentsView(WebDriver driver)
    {
        init(driver,this);
    }

    /**
     * Appuie sur le bouton annuler
     */
    public void clickCancelButton()
    {
        super.click(cancelBtn);
    }
}
