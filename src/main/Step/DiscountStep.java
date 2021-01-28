package Step;

import Helpers.Element.WebElementHelper;
import Step.Value.DiscountStepValue;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;

public class DiscountStep {

    // Timeout pour la valeur des éléments testés
    private static final int WAIT_FOR_VALUE_TIMEOUT_IN_SEC = 5;

    /**
     * Vérifie l'affichage d'une promotion
     * @return
     */
    @Step("Vérifie que la promotion {value.discountLabel} est présente")
    public static void isDiscountPresent(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification de la présence
        value.isNotNull(view.getDiscountLineAmount(value.expectedValue.toString()));
    }

    @Step("Vérifie que la promotion {value.discountLabel} n'est pas présente")
    public static void isDiscountNotPresent(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification de l'absence
        value.isNull(view.getDiscountLineAmount(value.expectedValue.toString()));
    }

    /**
     * Vérifie le montant de la promotion
     * @param value
     */
    @Step("Vérifie que la promotion {value.discountLabel} est égale à {value.expectedValue}")
    public static void checkDiscountLineAmount(DiscountStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification du montant
        value.isEquals(WebElementHelper.waitUntilExpectedText(value.getExpectedValue(), view.getDiscountLineAmount(value.discountLabel), WAIT_FOR_VALUE_TIMEOUT_IN_SEC, false));
    }

}
