package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class PruebaModificarUsuarioAdmin {

	@Given("en la vista administrador {string},{string}, {string}, {string}, {string}, {string} y {string}")
	public void en_la_vista_administrador_y(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
	    // Write code here that turns the phrase above into concrete actions
	}
	
	@When("{string}, {string} son distintos")
	public void son_distintos(String dato, String nuevoDato) {
	    // Write code here that turns the phrase above into concrete actions
	}

	@Then("se ha modificado el dato")
	public void se_ha_modificado_el_dato() {
	    // Write code here that turns the phrase above into concrete actions
	}	
	
	
}
