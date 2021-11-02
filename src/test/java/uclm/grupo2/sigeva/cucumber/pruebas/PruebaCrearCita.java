package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.UsuarioController;

public class PruebaCrearCita {

	@Given("hola")
	public void hola() {
		CitasController CitaCtrl = new CitasController();
		CitaCtrl.insertarCita();
	}

	@Then("se crea una cita de vacunacion")
	public void se_crea_una_cita_de_vacunacion() {

	}

}
