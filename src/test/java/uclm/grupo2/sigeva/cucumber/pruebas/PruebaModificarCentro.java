package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class PruebaModificarCentro {

	@Given("los datos de {string}, {string}, {string} y {string}")
	public void los_datos_de_y(String string, String string2, String string3, String string4) {
	    // Write code here that turns the phrase above into concrete actions
	}

	@When("{string}, {string} no coinciden")
	public void no_coinciden(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("se ha modificado la informacion del centro")
	public void se_ha_modificado_la_informacion_del_centro() {
	    // Write code here that turns the phrase above into concrete actions
	}	
}
