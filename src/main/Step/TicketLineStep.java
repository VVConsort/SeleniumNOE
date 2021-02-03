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

    @Step("Vérifie que le total de la prestation {serviceLabel} est égale à {value.expectedValue}")
    public static void checkServiceGrossPrice(String serviceLabel, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getServiceGrossPriceElem(serviceLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que la quantité de la prestation {serviceLabel} est égale à {value.expectedValue}")
    public static void checkServiceQuantity(String serviceLabel, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getServiceQuantityElem(serviceLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

    @Step("Vérifie que le prix unitaire de la prestation {serviceLabel} est égal à {value.expectedValue}")
    public static void checkServiceUnitPrice(String serviceLabel, BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getServiceUnitPriceElem(serviceLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }
}
