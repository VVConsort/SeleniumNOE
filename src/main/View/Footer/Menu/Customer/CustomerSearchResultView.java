package View.Footer.Menu.Customer;

import View.BaseView;
import View.CustomerCreateView;
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

    public CustomerSearchResultView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur nouveau client
     * @return
     */
    public CustomerCreateView clickOnNewCustomer() {
        // Click sur nouveau client
        super.click(newCustomerBtn);
        // Retourne la vue création client
        return new CustomerCreateView(driver);
    }

    /**
     * Click sur Fermer
     */
    public void clickClose() {
        super.click(closeBtn);
    }
}
