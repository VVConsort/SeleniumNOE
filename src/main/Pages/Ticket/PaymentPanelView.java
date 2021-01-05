package Pages.Ticket;

import Enums.PaymentMean;
import Helpers.Element.WebElementHelper;
import Pages.BasePage;
import Pages.Ticket.Payment.CreditNote.CreditNoteSearchView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPanelView extends BasePage {
    // Xpath boutton "Tout payer"
    private static final String PAY_ALL_BTN_XPATH = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_toolbarpane_payment_paymentTabContent_exactbutton\"]";

    // Boutton 'Avoir'
    @FindBy(xpath = "//button[@id=\"terminal_containerWindow_pointOfSale_multiColumn_rightPanel_rightBottomPanel_keyboard_toolbarcontainer_toolbarPayment_btnSide3_GCNV_payment.creditnote_button\"]")
    private WebElement creditNoteBtn;

    // Mode de paiement sélectionné
    private PaymentMean selectedPayment;

    public PaymentPanelView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Clique sur le boutton "Avoir"
     */
    public void clickCreditNoteBtn() {
        super.click(creditNoteBtn);
        selectedPayment = PaymentMean.CREDIT_NOTE;
    }

    /**
     * Appuie sur le boutton "Tout payer"
     */
    public BasePage clickPayAllBtn() {
        // La vue à renvoyer selon le mode dde paiement
        BasePage paymentPage = null;
        // On recherche l'élement
        WebElement payAllBtn = WebElementHelper.getElement(driver, By.xpath(PAY_ALL_BTN_XPATH));
        // Si il existe
        if (payAllBtn != null) {
            super.click(payAllBtn);
        }
        // On renvoie la vue correspondant au mode de paiement sélectionné
        switch (selectedPayment) {
            case CREDIT_NOTE -> paymentPage = new CreditNoteSearchView(driver);
            // TODO : les autres mode de paiement
        }
        return paymentPage;
    }


}
