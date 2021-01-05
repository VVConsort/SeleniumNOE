package Step;

import Pages.Header.HeaderView;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * Classe utilitaire de scan
 */
public class ScanStep {

    /**
     * Scan une valeure
     * @param value
     * @param driver
     * @throws InterruptedException
     */

    public static void scanValue(String value, WebDriver driver) throws InterruptedException {
        doScan(value, driver);
    }

    public static void scanValue(String value, WebDriver driver, int scanCount) throws InterruptedException {
        for (int i = 0; i < scanCount; i++) {
            doScan(value, driver);
        }
    }

    /**
     * Scan les valeures
     * @param values
     * @param driver
     * @throws InterruptedException
     */
    public static void scanValues(String[] values, WebDriver driver) throws InterruptedException {
        for (String value : values) {
            doScan(value, driver);
        }
    }

    /**
     * Scan la valeur
     * @param value
     * @param driver
     */
    @Step("Scan de la valeur : {value}")
    private static void doScan(String value, WebDriver driver) throws InterruptedException {
        // Header OB
        HeaderView headerView = new HeaderView(driver);
        // Scan
        headerView.clickOnScanBtn().typeAndValidateValue(value);
    }
}
