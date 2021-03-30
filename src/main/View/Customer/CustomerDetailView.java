package View.Customer;

import View.BaseView;
import View.Customer.Edit.CustomerEditView;
import View.Customer.Edit.CustomerHandleAddressView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerDetailView extends BaseView {

    // Btn "Modifier"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerView_footer_editCustomerFooter_buttonContainer_editbp2\"]")
    private WebElement modifyBtn;

    // Btn "Gérer Adresse(s)"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerView_footer_editCustomerFooter_buttonContainer_editbp2\"]")
    private WebElement handleAddressBtn;

    public CustomerDetailView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Ouvre la vue modification client
     */
    public CustomerEditView modifyCustomer() {
        super.click(modifyBtn, false);
        return new CustomerEditView(driver);
    }

    /**
     * Ouvre la vue de modification d'adresse
     * @return
     */
    public CustomerHandleAddressView modifyAddress() {
        super.click(handleAddressBtn, false);
        return new CustomerHandleAddressView(driver);
    }


}
