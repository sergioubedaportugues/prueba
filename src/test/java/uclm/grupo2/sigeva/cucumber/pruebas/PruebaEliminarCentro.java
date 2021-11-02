package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.model.CentroSalud;
import io.cucumber.java.en.Then;

public class PruebaEliminarCentro {
	
	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Given("los atributos {string}, {string}, {string}, {string}, {string} y {string}")
	public void los_atributos_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin, String cupo) {
		nombreCentro= "Miguelturra24"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00"; cupo="4";
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setCupo(cupo);
		CentroCtrl.insertarCentro(centro);
		assertEquals("Centro eliminado",CentroCtrl.borrarCentro(centro));
	}

	@Then("se elimina el centro de salud")
	public void se_elimina_el_centro_de_salud() {

	}

}


