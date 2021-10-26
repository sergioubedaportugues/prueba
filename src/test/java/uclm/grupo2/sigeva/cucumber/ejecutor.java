package uclm.grupo2.sigeva.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(features = "sigeva/src/test/java/features",
		glue = "uclm.grupo2.sigeva.cucumber.pruebas",
		plugin = { "pretty" }

)

public class ejecutor {

}
