package Pages.Ticket.Payment.CreditNote;

import Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditNoteUnitView extends BasePage {

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
    }
}
