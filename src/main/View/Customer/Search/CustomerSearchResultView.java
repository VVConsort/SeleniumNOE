package View.Customer.Search;

import View.BaseView;
import View.Customer.Edit.CustomerEditView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerSearchResultView extends BaseView {

    // Boutton Nouveau client
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomer_footer_modalBpSelectorFooter_newAction\"]")
    private WebElement newCustomerBtn;

    // Boutton Fermer
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomer_footer_modalBpSelectorFooter_modalDialogButton\"]")
    private WebElement closeBtn;

    // Premier résultat de la recherche client
    @FindBy(className = "enyo-tool-decorator obUiBaseButton obUiSelectButton obUiListItemButton obUiListSelectorLine obUiListBpsSelectorLine")
    private WebElement firstSearchResult;

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
     * Click sur le premier résultat de la recherche
     */
    public void clickOnFirstSearchResult() {
        super.click(firstSearchResult, false);
    }
}
