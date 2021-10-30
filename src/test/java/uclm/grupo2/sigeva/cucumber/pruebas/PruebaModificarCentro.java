package uclm.grupo2.sigeva.cucumber.pruebas;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class PruebaModificarCentro {

	@Given("los datos de {string}, {string} y {string}")
	public void los_datos_de_y(String nombreCentro, String direccionCentro, String numeroVacunas) {
	    
		/*  Pasos para insertar un NUEVO CENTRO si se ejecutan en orden los test no hace falta
		CentroSalud centro = new CentroSalud();
		nombreCentro= "Zendal"; direccionCentro="C/Avenida Parque 8"; numeroVacunas="7780";  
		centro.setnombreCentro(nombreCentro);
		centro.setdireccionCentro(direccionCentro);
		centro.setnumeroVacunas(numeroVacunas);;
		CentroSaludController CentroCtrl = new CentroSaludController();
		CentroCtrl.insertarCentro(centro);
		*/ 
		
		/* Modificado del centrol
		String direccionCentroN;
		centro.setdireccionCentro(direccionCentroN);
		CentroCtrl.modificarCentro(centro);
		*/
	}


	@Then("se ha modificado la informacion del centro")
	public void se_ha_modificado_la_informacion_del_centro() {

	}	
}
