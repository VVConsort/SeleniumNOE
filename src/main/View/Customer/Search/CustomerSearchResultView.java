package View.Customer.Search;

import Helpers.Element.WebElementHelper;
import View.BaseView;
import View.Customer.Edit.CustomerEditView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerSearchResultView extends BaseView {

    // ID Premier resultat de la recherche client
    private static final String FIRST_CUST_SEARCH_RESULT_ID = "terminal_containerWindow_pointOfSale_modalcustomer_body_listBpsSelector";
    // Boutton Nouveau client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomer_footer_modalBpSelectorFooter_newAction\"]")
    private WebElement newCustomerBtn;

    /* Premier résultat de la recherche client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomer_body_listBpsSelector_stBPAssignToReceipt_tbody_control_listBpsSelectorLine\"]")
    private WebElement firstSearchResult;*/
    // Boutton Fermer
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomer_footer_modalBpSelectorFooter_modalDialogButton\"]")
    private WebElement closeBtn;

    public CustomerSearchResultView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur nouveau client
     * @return
     */
    public CustomerEditView clickOnNewCustomer() {
        // Click sur nouveau client
        super.click(newCustomerBtn, false);
        // Retourne la vue création client
        return new CustomerEditView(driver);
    }

    /**
     * Click sur Fermer
     */
    public void clickClose() {
        super.click(closeBtn, false);
    }

    /**
     * Click sur le client passé en paramètre pour l'associer au ticket
     * @param customerText
     * @param throwException
     */
    public void linkCustomer(String customerText, boolean throwException) {
        // Recherche de l'élément représentant le client à partir de son texte et de l'id du composant
        WebElement custElem = WebElementHelper.getElementFromIdAndText(FIRST_CUST_SEARCH_RESULT_ID, customerText, driver, ELEMENT_MISSING_TIMEOUT, throwException);
        // Blindage
        if (custElem != null) {
            super.click(custElem, false);
        }
    }
}
