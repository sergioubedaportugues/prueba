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

public class PruebaModificarCita {
	
	@Autowired
	private CitasController CitasCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("un user con sesion iniciada")
	public void un_user_con_sesion_iniciada() {
		
		Optional<Usuario> optUser = user.findByLogin("paciente");
		LoginCtrl.iniciarSesion(optUser.get());	
		CitasCtrl.insertarCita();
		
		List <Citas> citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		citasUser.get(1).setDia("20-12-2021");
		citasUser.get(1).setHoras("11:25");
		assertEquals("Cita modificada",CitasCtrl.modificarCita(citasUser.get(1)));
		
		cita.deleteAll(cita.getByPacienteOrderByNumCitaAsc(optUser.get()));
		LoginCtrl.cerrarSesion();

	}

	@Then("se modfifica su cita")
	public void se_modfifica_su_cita() {

	}


}
