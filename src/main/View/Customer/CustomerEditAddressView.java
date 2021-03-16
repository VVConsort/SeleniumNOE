package View.Customer;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerEditAddressView extends BaseView {

    @FindBy(xpath = "/*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_addressLine1_coreElementContainer_addressLine1\"]")
    private WebElement adrLine1Input;
    @FindBy(xpath = "/*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_addressLine1_coreElementContainer_addressLine2\"]")
    private WebElement adrLine2Input;
    @FindBy(xpath = "/*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_addressLine1_coreElementContainer_addressLine3\"]")
    private WebElement adrLine3Input;
    @FindBy(xpath = "/*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_addressLine1_coreElementContainer_addressLine4\"]")
    private WebElement adrLine4Input;
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_customerAddrPostalCode_coreElementContainer_customerAddrPostalCode\"]")
    private WebElement postalCodeInput;
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_customerAddrCity_coreElementContainer_customerAddrCity\"]")
    private WebElement cityInput;
    @FindBy(xpath ="//*[@id=\"terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_footer_subwindowNewCustomer_footer_newcustomeraddrsave\"]")
    private WebElement saveBtn;

    // ville
    //*[@id="terminal_containerWindow_pointOfSale_customerAddrCreateAndEdit_body_edit_createcustomers_impl_customerAddrAttributes_line_customerAddrCity_coreElementContainer_customerAddrCity"]
    public CustomerEditAddressView(ChromeDriver driver) {
        init(driver, this);
    }

    public void setLine1(String line) {
        sendKeys(adrLine1Input, line);
    }

    public void setLine2(String line) {
        sendKeys(adrLine2Input, line);
    }

    public void setLine3(String line) {
        sendKeys(adrLine3Input, line);
    }

    public void setLine4(String line) {
        sendKeys(adrLine4Input, line);
    }

    public void setPostalCode(String postalCode) {
        sendKeys(postalCodeInput, postalCode);
    }

    public void setCity(String city) {
        sendKeys(cityInput, city);
    }

    public void save(){
        super.click(saveBtn,false);
    }

}
