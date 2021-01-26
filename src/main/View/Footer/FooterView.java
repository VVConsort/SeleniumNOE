package View.Footer;

import View.BaseView;
import View.Footer.Menu.MenuItemListView;
import View.Ticket.Payment.PaymentPanelView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterView extends BaseView {

    // XPath du bouton "Confirmer suppression"
    private static final String CONFIRM_DELETE_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_modalConfirmReceiptDelete_footer_btnModalApplyDelete\"]";
    // Menu
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_mainMenu_menuHolder_mainMenuButton\"]")
    private WebElement menuBtn;
    // Bouton "Payer"
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay\"]")
    private WebElement totalToPayBtn;
    // A payer
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay_icon_totalPrinter\"]")
    private WebElement totalToPay;
    // Etat
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_multiColumn_leftToolbar_leftToolbar_toolbar_btnTotalToPay_label_labelText_0\"]")
    private WebElement orderStateLbl;
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
        if (isElementPresentOnView(CONFIRM_DELETE_BTN_XPATH)) {
            super.searchAndClickElement(CONFIRM_DELETE_BTN_XPATH);
        }
    }

    /**
     * Click sur le bouton 'A payer' et retourne la panneau des paiements
     */
    public PaymentPanelView clickOnTotalToPayBtn() {
        super.click(totalToPayBtn);
        return new PaymentPanelView(driver);
    }

    /**
     * Click sur le bouton 'Menu'
     */
    public MenuItemListView clickOnMenuBtn() {
        super.click(menuBtn);
        // Renvoi la liste des éléments du menu
        return new MenuItemListView(driver);
    }

    /**
     * Retourne l'état de la commande
     * @return
     */
    public String getOrderState() {
        return orderStateLbl.getText();
    }

    /**
     * Retourne le montant à payer
     * @return
     */
    public String getTotalToPay() {
        return totalToPay.getText();
    }
}
