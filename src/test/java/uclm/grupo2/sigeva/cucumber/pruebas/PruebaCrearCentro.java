package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.model.CentroSalud;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
public class PruebaCrearCentro{
	
	@Autowired
	private CentroSaludController CentroCtrl;

	@Given("un {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void un_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin,String franja, String cupo) {
		
		nombreCentro= "MiguelturraTest23"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00";franja="6"; cupo="5";
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setFranja(franja);
		centro.setCupo(cupo);
		assertEquals("Centro con id: "+centro.getId(),CentroCtrl.insertarCentro(centro));
		CentroCtrl.borrarCentro(centro);
	}

	@Then("se crea un centro de salud")
	public void se_crea_un_centro_de_salud() {
		
	}

}