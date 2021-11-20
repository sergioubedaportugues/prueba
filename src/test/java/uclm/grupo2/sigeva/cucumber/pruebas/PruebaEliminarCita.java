package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
public class PruebaEliminarCita {
	
	@Autowired
	private CitasController CitasCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("un user loggeado")
	public void un_user_loggeado() {
		Optional<Usuario> optUser = user.findByLogin("paciente");
		LoginCtrl.iniciarSesion(optUser.get());	
		assertEquals("Cita creada",CitasCtrl.insertarCita());
		
		List <Citas> citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		CitasCtrl.borrarCita(citasUser.get(0));
		CitasCtrl.borrarCita(citasUser.get(1));
		citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		assertEquals(true,citasUser.isEmpty());
		LoginCtrl.cerrarSesion();

	}

	@Then("se eliminan las citas")
	public void se_eliminan_las_citas() {

	}

}
