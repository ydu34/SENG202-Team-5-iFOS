package stepDefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        features="src/test/resources/gui.group5",
        glue="stepDefinitions"

)

public class RunCucumberTest {

}
