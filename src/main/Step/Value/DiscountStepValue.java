package Step.Value;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class DiscountStepValue extends BaseStepValue {

    public String discountLabel;
    public String associatedProduct;

    public DiscountStepValue(Object expectedValue, String discountLabel, String associatedProduct, WebDriver driver, SoftAssert soft, boolean isHardAssert) {
        super(expectedValue, driver, soft, isHardAssert);
        this.discountLabel = discountLabel;
        this.associatedProduct = associatedProduct;
    }
}
