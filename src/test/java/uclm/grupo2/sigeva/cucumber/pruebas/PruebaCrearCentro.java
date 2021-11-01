package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import io.cucumber.java.en.Then;

public class PruebaCrearCentro {
	
	@Given("un {string}, {string}, {string}, {string}, {string} y {string}")
	public void un_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin, String cupo) {
		
		nombreCentro= "Zendal"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00"; cupo="4";
		CentroSalud centro = new CentroSalud(nombreCentro,direccionCentro,numeroVacunas,fInicio,fFin,cupo);
		CentroSaludController CentroCtrl = new CentroSaludController();
		CentroCtrl.insertarCentro(centro);
	}

	@Then("se crea un centro de salud")
	public void se_crea_un_centro_de_salud() {

	}

}