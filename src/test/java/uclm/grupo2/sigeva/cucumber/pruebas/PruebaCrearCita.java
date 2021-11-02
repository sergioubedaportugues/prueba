package uclm.grupo2.sigeva.cucumber.pruebas;


import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import uclm.grupo2.sigeva.http.CitasController;
import io.cucumber.java.en.Then;



public class PruebaCrearCita {
	
	@Autowired
	private CitasController CitasCtrl;

	
	@Then("se crea una cita de vacunacion")
	public void se_crea_una_cita_de_vacunacion() {
		assertEquals("Cita creada",CitasCtrl.insertarCita());
	}

}
