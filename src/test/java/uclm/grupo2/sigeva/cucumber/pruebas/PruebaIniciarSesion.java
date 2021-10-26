package uclm.grupo2.sigeva.cucumber.pruebas;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PruebaIniciarSesion {
	
	@Given("^acceso con \"([^\"]*)\" y \"([^\"]*)\" correctos$")
	public void acceso_con_y_correctos(String login, String password) throws Exception {
	
	}
	
	@When("^los datos son correctos$")
	public void los_datos_son_correctos() {

	}

	@Then("^accedo a la pantalla principal$")
	public void accedo_a_la_pantalla_principal() {

	}
	
	@Given("^acceso con \"([^\"]*)\" y \"([^\"]*)\"$")
	public void acceso_con_y(String login, String password) throws Exception {
	
	}
	
	@When("^los datos son incorrectos$")
	public void los_datos_son_incorrectos() {

	}

	@Then("^se lanza una excepcion$")
	public void se_lanza_una_excepcion() {

	}
}
