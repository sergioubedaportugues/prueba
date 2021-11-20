package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Then;
public class PruebaBorrarCentro {
	
	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("los atributos {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void los_atributos_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin,String franja, String cupo) {
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
		LoginCtrl.iniciarSesion(optUser.get());
		
		nombreCentro= "Miguelturra24"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "08:00"; fFin = "14:00"; franja="6"; cupo="5";
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setFranja(franja);
		centro.setCupo(cupo);
		CentroCtrl.insertarCentro(centro);
		assertEquals("Centro eliminado",CentroCtrl.borrarCentro(centro));
	}

	@Then("se elimina el centro")
	public void se_elimina_el_centro() {

	}

}
