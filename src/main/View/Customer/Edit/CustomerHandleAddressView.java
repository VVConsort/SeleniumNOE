package View.Customer.Edit;

import Helpers.Element.WebElementHelper;
import Helpers.XPath.XPathCustomerAddressHelper;
import View.BaseView;
import View.Customer.Edit.CustomerEditAddressView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CustomerHandleAddressView extends BaseView {

    // Séparateur code postal et ville
    private static final String ADDRESS_IDENTIFIER_SEPARATOR = "-";
    // Bouton "Afficher details" du menu contextuel de l'adresse
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomeraddress_body_listBpsLoc_bpsloclistitemprinter_tbody_control_listBpsLocLine_btnContextMenu_menu_bPLocDetailsContextMenuItem\"]")
    private WebElement openDetailsContextMenuBtn;
    // Botuon "Modifier" du men contextuel de l'adresse
    @FindBy(xpath="//*[@id=\"terminal_containerWindow_pointOfSale_modalcustomeraddress_body_listBpsLoc_bpsloclistitemprinter_tbody_control2_listBpsLocLine_btnContextMenu_menu_bPLocEditContextMenuItem\"]")
    private WebElement modifyContextMenuBtn;

    public CustomerHandleAddressView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Retourne l'élement "Identifiant adress" à partir du code postal et de la ville
     * @return
     */
    private String getAddressLineElemId(String postalCode, String city) {
        String result = "";
        // Recherche l'élement 'identifiant adress ' à partir du code postal et de la ville
        WebElement addressElem = WebElementHelper.getElementFromText(driver, getAddressIdentifier(postalCode, city), ELEMENT_MISSING_TIMEOUT, false);
        // Blindage
        if (addressElem != null) {
            // Affectation de l'id de l'élement
            result = addressElem.getAttribute("id");
        }
        return result;
    }

    /**
     * Ouvre la vue édition d'une adresse à partir de son code postal et de sa ville
     * @param postalCode
     * @param city
     * @return
     */
    public CustomerEditAddressView editAddress(String postalCode, String city) {
        // Récupération de l'id de l'adresse
        String addressId = getAddressLineElemId(postalCode, city);
        // Récupération du xpath du bouton contextuel associé et click
        String contextBtnXPth = XPathCustomerAddressHelper.getAddressContextBtnXPath(addressId);
        WebElement contextBtn = WebElementHelper.waitUntilElementIsVisible(driver, ELEMENT_MISSING_TIMEOUT, By.xpath(contextBtnXPth), false);
        // Si le bouton est trouvé
        if (contextBtn != null) {
            // Click sur le menu contextuel
            super.click(contextBtn, false);
            // Click sur "Modfier"
            super.click(modifyContextMenuBtn, false);
            return new CustomerEditAddressView(driver);
        }
        return null;
    }

    /**
     * Retourne l'identifiant de l'adresse
     * @param postalCode
     * @param city
     * @return
     */
    private String getAddressIdentifier(String postalCode, String city) {
        return postalCode + " " + ADDRESS_IDENTIFIER_SEPARATOR + " " + city;
    }

}
