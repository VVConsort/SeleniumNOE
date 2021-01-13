package Step;

import Step.Value.BaseStepValue;
import Step.Value.DiscountStepValue;
import View.Ticket.ReceiptView;
import io.qameta.allure.Step;

public class DiscountStep {

    /**
     * Vérifie l'affichage d'une promotion
     * @return
     */
    @Step("Vérifie que la promotion {discountLabel} est présente")
    public static void isDiscountPresent(BaseStepValue value) {
        ReceiptView view = new ReceiptView(value.driver);
        // Vérification de la présence
        value.isNotNull(view.getDiscountLineAmount(value.expectedValue.toString()));
    }

    @Step("Vérifie que la promotion {discountLabel} n'est pas présente")
    public static void isDiscountNotPresent(BaseStepValue value) {
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
        value.isEquals(view.getDiscountLineAmount(value.discountLabel));
    }

}
