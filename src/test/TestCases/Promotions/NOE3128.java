package TestCases.Promotions;

import Helpers.Test.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.CucumberOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

@Parameters({"voucherCode"})
@CucumberOptions(features = {"src/test/Features"}, tags = "@TEST_NOE-3128", plugin = {"json:target/cucumber-report.json"})
public class NOE3128 extends BaseTest {
    private String expectedTotal;

    @BeforeTest
    @Parameters({"expectedTotal"})
    public void getParameters(String expectedTotal) {
        this.expectedTotal = expectedTotal;
    }

    @Given("give")
    public void give() {
        Assert.assertTrue(true);
    }

    @When("when")
    public void when() {
        Assert.assertTrue(true);
    }

    @Then("then")
    public void then() {
        Assert.assertEquals("666", expectedTotal);
    }
}
