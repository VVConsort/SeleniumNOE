package View.Log;

import View.BaseView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Page loggin OB
 */
public class LogScreenView extends BaseView {

    // Champ utilisateur
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_login_formElementUsername_coreElementContainer_username\"]")
    WebElement userNameField;

    // Champ mot de passe
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_login_formElementPassword_coreElementContainer_password\"]")
    WebElement passwordField;

    // Bouton connexion
    @FindBy(xpath = "//*[@id=\"terminal_containerWindow_login_loginButton\"]")
    WebElement connectionBtn;

    public LogScreenView(ChromeDriver webDriver) {
        init(webDriver, this);
    }

    /**
     * Renseigne le nom d'utilisateur
     * @param username
     */
    public void setUserName(String username) {
        userNameField.sendKeys(username);
    }

    /**
     * Renseigne le mot de passe
     * @param password
     */
    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    /**
     * Clic sur Connection
     */
    public void clickConnexion() {
        super.click(connectionBtn, false);
    }

}
