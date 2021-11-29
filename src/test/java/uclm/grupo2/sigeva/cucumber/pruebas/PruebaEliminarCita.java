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
import uclm.grupo2.sigeva.model.CitasDTO;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
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
		
		List <Citas> citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		CitasDTO cDTO= new CitasDTO();
		CitasDTO cDTO1= new CitasDTO();
		cDTO.setIdDTO(citasUser.get(0).getId());
		cDTO.setHorasDTO(citasUser.get(0).getHoras());
		cDTO.setDiaDTO(citasUser.get(0).getDia());
		cDTO.setPacienteDTO(citasUser.get(0).getPaciente());
		cDTO.setCsDTO(citasUser.get(0).getCs());
		cDTO.setNumCitaDTO(citasUser.get(0).getNumCita());
		cDTO.setAplicadaDTO(citasUser.get(0).isAplicada());
		
		cDTO1.setIdDTO(citasUser.get(1).getId());
		cDTO1.setHorasDTO(citasUser.get(1).getHoras());
		cDTO1.setDiaDTO(citasUser.get(1).getDia());
		cDTO1.setPacienteDTO(citasUser.get(1).getPaciente());
		cDTO1.setCsDTO(citasUser.get(1).getCs());
		cDTO1.setNumCitaDTO(citasUser.get(1).getNumCita());
		cDTO1.setAplicadaDTO(citasUser.get(1).isAplicada());
		
		CitasCtrl.borrarCita(cDTO);
		CitasCtrl.borrarCita(cDTO1);
		citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		assertEquals(true,citasUser.isEmpty());
		LoginCtrl.cerrarSesion();

	}

	@Then("se eliminan las citas")
	public void se_eliminan_las_citas() {

	}

}
