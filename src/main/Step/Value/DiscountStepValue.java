package Step.Value;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class DiscountStepValue extends BaseStepValue {

    public String discountLabel;
    public String associatedProduct;

    public DiscountStepValue(ChromeDriver driver, SoftAssert soft, boolean isHardAssert) {
        super( driver, soft, isHardAssert);
    }
}
