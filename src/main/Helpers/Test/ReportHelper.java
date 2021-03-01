package Helpers.Test;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReportHelper {

    /**
     * Prend un screenshot et l'attache au rapport
     * @return
     */
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] attachScreenshot(ChromeDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Prend un screenshot et l'attache au rapport
     * @return
     */
    @Attachment(value = "{methodName}", type = "image/png")
    public static byte[] attachScreenshot(ChromeDriver driver,String methodName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
