package TestCases.Promotions;

import Helpers.Test.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Parameters;

@Parameters({"voucherCode"})
@CucumberOptions(features = {"src/test/Features"},tags = "@TEST_NOE-3130")
public class NOE3130 extends BaseTest {


    @Given("I have entered {int} into the calculator")
    public void iHaveEnteredInput_IntoTheCalculator(int arg0) {
        System.out.println("Given :  " +arg0);
    }

    @When("I press {string}")
    public void iPressButton(String arg0) {
        System.out.println("When :  " +arg0);
    }

    @Then("the result should be {int} on the screen")
    public void theResultShouldBeOutputOnTheScreen(int arg0) {
        System.out.println("Then :  " +arg0);
    }


}
