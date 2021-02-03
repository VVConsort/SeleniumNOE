package View.Ticket.Payment;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class SendTicketView extends BaseView {
    // Boutton "Email seulement"
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_pointOfSale_obsmail_getEmailAddressModal_emailAddressModal__btnEmailOnly\"]")
    private WebElement mailOnlyBtn;

    public SendTicketView(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Click sur le bouton "Email seulement"
     */
    public void clickMailOnly() {
        super.click(mailOnlyBtn);
    }
}
