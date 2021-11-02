package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.model.CentroSalud;
import io.cucumber.java.en.Then;

public class PruebaModificarCentro {
	
	@Autowired
	private CentroSaludController CentroCtrl;

	@Given("los datos de {string}, {string}, {string}, {string}, {string} y {string}")
	public void los_datos_de_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin, String cupo) {
		nombreCentro= "Miguelturra33"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00"; cupo="4";
		String nombreCentroN= "Miguelturra34"; String direccionCentroN="Avenida Parque 6"; String numeroVacunasN="8780";
		String fInicioN = "09:30"; String fFinN = "14:00";String cupoN="4";
		
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setCupo(cupo);
		CentroCtrl.insertarCentro(centro);
		
		centro.setNombre(nombreCentroN);
		centro.setDireccion(direccionCentroN);
		centro.setNumVacunas(numeroVacunasN);
		centro.setfInicio(fInicioN);
		centro.setfFin(fFinN);
		centro.setCupo(cupoN);
		
		assertEquals("Centro modificado",CentroCtrl.modificarCentro(centro));
	}

	@Then("se ha modificado la informacion del centro")
	public void se_ha_modificado_la_informacion_del_centro() {

	}
	
}
