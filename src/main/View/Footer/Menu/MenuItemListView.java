package View.Footer.Menu;

import View.BaseView;
import View.Customer.Search.CustomerSearchView;
import View.Footer.Menu.Voucher.AddVoucherView;
import View.Footer.Menu.Voucher.RemoveVoucherView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class MenuItemListView extends BaseView {

    // Boutton "Bon d'achat"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_menuScroller_menuCoupons\"]")
    private WebElement voucherBtn;

    // Bouton "Enlever bon d'achat du ticket"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_menuScroller_menuRemoveCoupons\"]")
    private WebElement removeVoucherBtn;

    // Boutton "Recherche client"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_menuScroller_menuCustomers\"]")
    private WebElement customerSearchBtn;

    public MenuItemListView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur "Bon d'achat"
     */
    public AddVoucherView clickOnVoucher() {
        super.click(voucherBtn, false);
        // Retourne le composant de saisie de bon d'achat
        return new AddVoucherView(driver);
    }

    /**
     * Click sur "Enlever bon d'achat du ticket"
     */
    public RemoveVoucherView clickOnRemoveVoucher() {
        super.click(removeVoucherBtn, false);
        // Retourne le composant de saisie de bon d'achat
        return new RemoveVoucherView(driver);
    }

    /**
     * Click sur 'Recherche clients'
     */
    public CustomerSearchView clickOnSearchCustomer() {
        super.click(customerSearchBtn, false);
        // Retourne la vue recherche client
        return new CustomerSearchView(driver);
    }

}
