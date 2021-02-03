package View.BackOffice;

import View.BaseView;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class LoggingPage extends BaseView {

    // Utilisateur
    @FindBy(xpath = "//*[@id=\"user\"]")
    private WebElement userField;

    // Mot de passe
    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    // Bouton connexion
    @FindBy(xpath = "//*[@id=\"buttonOK\"]")
    private WebElement logBtn;

    public LoggingPage(ChromeDriver driver) {
        init(driver, this);
    }

    /**
     * Saisie le nom d'utilisateur
     * @param user
     */
    public void setUserName(String user) {
        userField.sendKeys(user);
    }

    /**
     * Saisie le mot de passe
     * @param password
     */
    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    /**
     * Click sur loggin
     */
    public void clickLogIn()
    {
        super.click(logBtn);
    }

}
