package View.Ticket.Payment.CreditNote;

import Helpers.Element.WebElementHelper;
import View.BaseView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CreditNoteUnitView extends BaseView {

    // Boutton 'Appliquer'
    @FindBy(xpath = " //*[@id=\"terminal_containerWindow_pointOfSale_GCNV_UI_Details_footer_okbutton\"]")
    private WebElement applyBtn;

    public CreditNoteUnitView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Appuie sur le boutton "Appliquer" de l'avoir
     */
    public void clickApplyBtn() {
        super.click(applyBtn, false);
        // On attend que le bouton disparaisse
        WebElementHelper.waitUntilElementIsNotPresent(driver, 5, By.id(applyBtn.getAttribute("id")), false);
    }
}