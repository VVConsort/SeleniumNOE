package Step;

import Helpers.Element.WebElementHelper;
import Step.Value.DiscountStepValue;
import Step.Value.BaseStepValue;
import Helpers.XPath.XPathHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DiscountStep {
    // Préfixe du libellé des promotions sur OB
    private static final String OB_DISCOUNT_LABEL_PREFIX = "-- ";

    /**
     * Vérifie l'affichage d'une promotion
     * @return
     */
    @Step("Vérifie que la promotion {discountLabel} est présente")
    public static void isDiscountPresent(BaseStepValue value) {
        value.isNotNull(getDiscountLabelELemByText(value.expectedValue.toString(), value.driver));
    }

    @Step("Vérifie que la promotion {discountLabel} n'est pas présente")
    public static void isDiscountNotPresent(BaseStepValue value) {
        value.isNotNull(getDiscountLabelELemByText(value.expectedValue.toString(), value.driver));
    }

    /**
     * Vérifie le montant de la promotion
     * @param value
     */
    @Step("Vérifie que la promotion {discountLabel} est égale à {value.expectedValue}")
    public static void checkDiscountLineAmount(DiscountStepValue value) {
        String discountLineAmount = "";
        // On cherche le label de la promotion
        WebElement discountLabelElem = getDiscountLabelELemByText(value.discountLabel, value.driver);
        // Si l'élément existe
        if (discountLabelElem != null) {
            // Récupération de l'ID du label
            String discountLabelId = discountLabelElem.getAttribute("id");
            // Récupération de l'ID du montant promo
            String amountXPath = XPathHelper.getLineDiscountGrossAmountXPath(XPathHelper.getBodyControlXPathVarFromProductId(discountLabelId), XPathHelper.getRenderOrderLineXPathVarFromProductId(discountLabelId), XPathHelper.getEndVariableFromProductId(discountLabelId));
            // On récupère le montant de la promo
            discountLineAmount = WebElementHelper.getTextFromElement(value.driver, By.xpath(amountXPath));
        }
        value.isEquals(discountLineAmount);
    }

    /**
     * Retourne l'élement libellé ligne de la promo
     * @param label
     * @return
     */
    private static WebElement getDiscountLabelELemByText(String label, WebDriver driver) {
        // Recherche l'élement 'label' à partir du texte
        return WebElementHelper.getElement(driver, By.xpath("*//div[text()='" + OB_DISCOUNT_LABEL_PREFIX + label + "']"));

    }
}
