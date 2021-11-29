package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
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
		UsuarioDTO uDTO= new UsuarioDTO();
		
		Optional<Usuario> optUser = user.findByLogin("paciente");
		uDTO.setIdDTO(optUser.get().getId());
		uDTO.setLoginDTO(optUser.get().getLogin());
		uDTO.setPasswordDTO(optUser.get().getPassword());
		uDTO.setNombreDTO(optUser.get().getNombre());
		uDTO.setApellidosDTO(optUser.get().getApellidos());
		uDTO.setTelefonoDTO(optUser.get().getTelefono());
		uDTO.setDniDTO(optUser.get().getDni());
		uDTO.setRolDTO(optUser.get().getRol());
		uDTO.setCsDTO(optUser.get().getCs());
		uDTO.setDosisDTO(optUser.get().getDosis());
		LoginCtrl.iniciarSesion(uDTO);	
		assertEquals("Cita creada",CitasCtrl.insertarCita());
		cita.deleteAll(cita.getByPacienteOrderByNumCitaAsc(optUser.get()));
		LoginCtrl.cerrarSesion();
	}

	@Then("se genera las citas")
	public void se_genera_las_citas() {

	}


}
