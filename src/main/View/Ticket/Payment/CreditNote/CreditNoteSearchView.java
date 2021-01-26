package View.Ticket.Payment.CreditNote;

import Helpers.Element.WebElementHelper;
import View.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditNoteSearchView extends BaseView {

    // XPath de la fenêtre unitaire "Avoir"
    private static final String CREDIT_NOTE_WINDOW_XPATH = "//*[@id=\"terminal_containerWindow_pointOfSale_GCNV_UI_Details_header\"]";
    // Champs de saisie numéro avoir
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_GCNV_UI_ModalGiftCards_body_giftcards_stGCAssignToReceipt_theader_modalGcScrollableHeader_filterSelector_formElementEntityFilterText_coreElementContainer_entityFilterText\"]")
    private WebElement creditNoteInput;
    // Bouton fermer
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_GCNV_UI_ModalGiftCards_modalCloseButton\"]")
    private WebElement closeWindowBtn;

    public CreditNoteSearchView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Recherche un avoir par son code
     * @param creditNoteNumber
     */
    public CreditNoteUnitView getCreditNote(String creditNoteNumber) {
        // Vue "Avoir"
        CreditNoteUnitView creditNoteUnitView = null;
        // On renseigne le numéro d'avoir
        creditNoteInput.sendKeys(creditNoteNumber);
        // Touche Entrée
        creditNoteInput.sendKeys(Keys.ENTER);
        // Attend que l'élément soit visible
        WebElement creditNoteWindow =   WebElementHelper.waitUntilElementIsVisible(driver,5, By.xpath(CREDIT_NOTE_WINDOW_XPATH),false);
        // Si l'élement "Vue unitaire avoir" est affiché c'est que le code correspond à un avoir valide
        //WebElement creditNoteWindow = Helpers.Element.WebElementHelper.getElement(driver, By.xpath(CREDIT_NOTE_WINDOW_XPATH));
        // Si il y a un avoir valide trouvé
        if (creditNoteWindow != null) {
            // Instanciation de la vue CreditNote unitaire
            creditNoteUnitView = new CreditNoteUnitView(driver);
        }
        return creditNoteUnitView;
    }

    /**
     * Ferme la fenêtre de recherche
     */
    public void closeCreditNoteSearchView() {
        super.click(closeWindowBtn);
    }
}
