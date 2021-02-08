package Step;

import Helpers.Element.WebElementHelper;
import Step.Value.BaseStepValue;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;

public class TicketLineStep extends BaseStep {

    @Step("Vérifie que le forfait {forfaitLabel} est égale à {value.expectedValue}")
    public static void checkForfaitPrice(String forfaitLabel, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getForfaitPriceElem(forfaitLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que le total de la ligne {label} est égale à {value.expectedValue}")
    public static void checkGrossPrice(String label, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getLineGrossPriceElem(label), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que la quantité de la ligne {label} est égale à {value.expectedValue}")
    public static void checkQuantity(String label, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getLineQuantityElem(label), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que le prix unitaire de la ligne {label} est égal à {value.expectedValue}")
    public static void checkUnitPrice(String label, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getUnitPriceElem(label), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }
}
