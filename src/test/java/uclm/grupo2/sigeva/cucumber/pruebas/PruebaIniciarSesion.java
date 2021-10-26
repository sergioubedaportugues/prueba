package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PruebaIniciarSesion {

	@Given("acceso con login y password correctos")
	public void acceso_con_y_correctos(String login, String password) throws Exception {
	
	}
	
	@When("los datos son correctos")
	public void los_datos_son_correctos() {

	}

	@Then("Accedo a la pantalla principal")
	public void accedo_a_la_pantalla_principal() {

	}
	
	@Given("acceso con login y password correctos")
	public void acceso_con_y(String login, String password) throws Exception {
	
	}
	
	@When("los datos son correctos")
	public void los_datos_son_incorrectos() {

	}

	@Then("Accedo a la pantalla principal")
	public void se_lanza_una_excepcion() {

	}
}
