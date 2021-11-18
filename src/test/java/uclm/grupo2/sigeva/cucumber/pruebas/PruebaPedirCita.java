package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PruebaPedirCita {
	
	@Autowired
	private CitasController CitasCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("un usuario loggeado")
	public void un_usuario_loggeado() {
		
		Optional<Usuario> optUser = user.findByLogin("paciente");
		LoginCtrl.iniciarSesion(optUser.get());	
		assertEquals("Cita creada",CitasCtrl.insertarCita());
		cita.deleteAll(cita.getByPacienteOrderByNumCitaAsc(optUser.get()));
		LoginCtrl.cerrarSesion();
	}

	@Then("se genera las citas")
	public void se_genera_las_citas() {

	}


}
