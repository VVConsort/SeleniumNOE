package View.Ticket.Payment.CreditNote;

import Helpers.Element.WaitHelper;
import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditNoteUnitView extends BaseView {

    // Boutton Appliquer
    //*[@id="terminal_containerWindow_pointOfSale_GCNV_UI_Details_footer_okbutton"]

    //*[@id="terminal_containerWindow_pointOfSale_GCNV_UI_Details_footer_okbutton"]

    // Boutton 'Appliquer'
    @FindBy(xpath = " //*[@id=\"terminal_containerWindow_pointOfSale_GCNV_UI_Details_footer_okbutton\"]")
    private WebElement applyBtn;

    public CreditNoteUnitView(WebDriver driver) {
        init(driver, this);
    }

    /**
     * Appuie sur le boutton "Appliquer" de l'avoir
     */
    public void clickApplyBtn()
    {
        super.click(applyBtn);
        // On attend que le bouton disparaisse
        WaitHelper.waitUntilComponentIsNotVisible(driver,5,applyBtn,false);
    }
}