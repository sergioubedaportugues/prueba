package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
//import uclm.grupo2.sigeva.http.CentroSaludController;
//import uclm.grupo2.sigeva.model.CentroSalud;
import io.cucumber.java.en.Then;

public class PruebaCrearCentro {
	
	@Given("un {string}, {string} y {string}")
	public void un_y(String nombreCentro, String direccionCentro, String numeroVacunas) {
		/*CentroSalud centro = new CentroSalud();
		nombreCentro= "Zendal"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; numeroSanitarios="123";  
		centro.setnombreCentro(nombreCentro);
		centro.setdireccionCentro(direccionCentro);
		centro.setnumeroVacunas(numeroVacunas);;
		CentroSaludController CentroCtrl = new CentroSaludController();
		CentroCtrl.insertarCentro(centro);*/
	}

	@Then("se crea un centro de salud")
	public void se_crea_un_centro_de_salud() {

	}

}