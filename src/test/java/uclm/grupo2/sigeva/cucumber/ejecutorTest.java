package uclm.grupo2.sigeva.cucumber;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
		glue = "uclm.grupo2.sigeva.cucumber.pruebas",
		plugin = { "pretty","junit:src/test/java/target/cucumber-reports/Cucumber.xml", "html:src/test/java/target/html-report","json:src/test/java/target/cucumber.json" }
)
public class ejecutorTest {

}
