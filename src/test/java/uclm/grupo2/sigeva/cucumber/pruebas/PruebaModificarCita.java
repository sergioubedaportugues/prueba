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
		CitasCtrl.insertarCita();
		
		List <Citas> citasUser = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		citasUser.get(1).setDia("30-12-2021");
		citasUser.get(1).setHoras("11:25");
		
		CitasDTO cDTO= new CitasDTO();
		cDTO.setId(citasUser.get(1).getId());
		cDTO.setHoras(citasUser.get(1).getHoras());
		cDTO.setDia(citasUser.get(1).getDia());
		cDTO.setPaciente(citasUser.get(1).getPaciente());
		cDTO.setCs(citasUser.get(1).getCs());
		cDTO.setNumCita(citasUser.get(1).getNumCita());
		cDTO.setAplicada(citasUser.get(1).isAplicada());
		assertEquals("Cita modificada",CitasCtrl.modificarCita(cDTO));
		
		cita.deleteAll(cita.getByPacienteOrderByNumCitaAsc(optUser.get()));
		LoginCtrl.cerrarSesion();

	}

	@Then("se modfifica su cita")
	public void se_modfifica_su_cita() {

	}


}
