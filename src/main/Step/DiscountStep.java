package Step;

import Helpers.Element.WebElementHelper;
import Step.Value.DiscountStepValue;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;

public class DiscountStep extends BaseStep {

    /**
     * Vérifie l'affichage d'une promotion
     * @return
     */
    @Step("Vérifie que la promotion {value.discountLabel} est présente")
    public static void isDiscountPresent(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification de la présence
        value.isNotNull(view.getDiscountLineAmountElem(value.expectedValue.toString()));
    }

    @Step("Vérifie que la promotion {value.discountLabel} n'est pas présente")
    public static void isDiscountNotPresent(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification de l'absence
        value.isNull(view.getDiscountLineAmountElem(value.expectedValue.toString()));
    }

    /**
     * Vérifie le montant de la promotion
     * @param value
     */
    @Step("Vérifie que la promotion {value.discountLabel} est égale à {value.expectedValue}")
    public static void checkDiscountLineAmount(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getDiscountLineAmountElem(value.discountLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false,value.driver));
    }

}
