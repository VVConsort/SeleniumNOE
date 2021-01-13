package View.Footer;

import View.BaseView;
import View.Footer.Menu.MenuItemListView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterView extends BaseView {

    // XPath du bouton "Confirmer suppression"
    private static final String CONFIRM_DELETE_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_modalConfirmReceiptDelete_footer_btnModalApplyDelete\"]";
    // Menu
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_mainMenuButton\"]")
    private WebElement menuBtn;
    // A payer
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay\"]")
    private WebElement totalToPayBtn;
    // Vider ticket
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_buttonDelete\"]")
    private WebElement deleteTicketBtn;

    public FooterView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Vide le ticket en cours
     */
    public void clickOnDeleteTicketBtn() {
        super.click(deleteTicketBtn);
    }

    /**
     * Click sur le bouton 'confirmer suppression ticket'
     */
    public void clickOnConfirmDeleteBtn() {
        super.searchAndClickElement(CONFIRM_DELETE_BTN_XPATH);
    }

    /**
     * Click sur le bouton 'A payer'
     */
    public void clickOnTotalToPayBtn() {
        super.click(totalToPayBtn);
    }

    /**
     * Click sur le bouton 'Menu'
     */
    public MenuItemListView clickOnMenuBtn() {
        super.click(menuBtn);
        // Renvoi la liste des éléments du menu
        return new MenuItemListView(driver);
    }
}
