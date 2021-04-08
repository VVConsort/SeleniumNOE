package TestCases.Promotions;

import Enums.Payment.PaymentMean;
import Helpers.Test.BaseTest;
import Step.LoggingStep;
import Step.PaymentStep;
import Step.ScanStep;
import Step.TicketStep;
import Step.Value.BaseStepValue;
import Step.Value.Payment.PaymentStepValue;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

@CucumberOptions(features = {"src/test/Features"}, tags = "@TEST_NOE-3129", plugin = "json:target/cucumber-report.json")
public class NOE3129 extends BaseTest {


    @Given("I have a ticket with product {string} in it")
    public void iHaveATicketWithProductInIt(String produtCode) throws MalformedURLException, InterruptedException {
        // Lance et se log sur OB
        driver = LoggingStep.launchAndLogToOpenBravo();
        // On vide le ticket
        TicketStep.deleteTicket(driver);
        // Ajout produit
        ScanStep.scanValue(produtCode, driver);
    }

    @When("I try to add the used voucher {string}")
    public void iTryToAddTheUsedVoucher(String arg0) {
        PaymentStepValue stepValue = getNewPaymentStepValue(true);
        stepValue.paymentMean = PaymentMean.VOUCHER;
        stepValue.paymentId = arg0;
        stepValue.expectedValue = true;
        PaymentStep.useVoucher(stepValue);
    }

    @Then("A message should say the voucher is already used")
    public void aMessageShouldSayTheVoucherIsAlreadyUsed() {
        System.out.println("wesh");
    }

    @And("The total should remains {string}")
    public void theTotalShouldRemains(String expectedTotal) {
        BaseStepValue step = getNewBaseStepValue(true);
        step.expectedValue = expectedTotal;
        TicketStep.checkTotalToPay(step);
    }
}
