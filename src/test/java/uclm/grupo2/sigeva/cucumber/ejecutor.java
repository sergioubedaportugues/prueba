package uclm.grupo2.sigeva.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/java/features",
		glue = "uclm.grupo2.sigeva.cucumber.pruebas",
		plugin = { "pretty" }
		// Prueba

)

public class ejecutor {

}
