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
		uDTO.setId(optUser.get().getId());
		uDTO.setLogin(optUser.get().getLogin());
		uDTO.setPassword(optUser.get().getPassword());
		uDTO.setNombre(optUser.get().getNombre());
		uDTO.setApellidos(optUser.get().getApellidos());
		uDTO.setTelefono(optUser.get().getTelefono());
		uDTO.setDni(optUser.get().getDni());
		uDTO.setRol(optUser.get().getRol());
		uDTO.setCs(optUser.get().getCs());
		uDTO.setDosis(optUser.get().getDosis());
		LoginCtrl.iniciarSesion(uDTO);	
		assertEquals("Cita creada",CitasCtrl.insertarCita());
		
		List <Citas> citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		CitasDTO cDTO= new CitasDTO();
		CitasDTO cDTO1= new CitasDTO();
		cDTO.setId(citasUser.get(0).getId());
		cDTO.setHoras(citasUser.get(0).getHoras());
		cDTO.setDia(citasUser.get(0).getDia());
		cDTO.setPaciente(citasUser.get(0).getPaciente());
		cDTO.setCs(citasUser.get(0).getCs());
		cDTO.setNumCita(citasUser.get(0).getNumCita());
		cDTO.setAplicada(citasUser.get(0).isAplicada());
		
		cDTO1.setId(citasUser.get(1).getId());
		cDTO1.setHoras(citasUser.get(1).getHoras());
		cDTO1.setDia(citasUser.get(1).getDia());
		cDTO1.setPaciente(citasUser.get(1).getPaciente());
		cDTO1.setCs(citasUser.get(1).getCs());
		cDTO1.setNumCita(citasUser.get(1).getNumCita());
		cDTO1.setAplicada(citasUser.get(1).isAplicada());
		
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
