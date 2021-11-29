package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.CentroSaludDTO;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
public class PruebaCrearCentro{
	
	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;

	@Given("un {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void un_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin,String franja, String cupo) {
		UsuarioDTO uDTO= new UsuarioDTO();
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
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
		
		nombreCentro= "MiguelturraTest23"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00";franja="6"; cupo="5";
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setFranja(franja);
		centro.setCupo(cupo);
		
		CentroSaludDTO csDTO= new CentroSaludDTO();
		csDTO.setId(centro.getId());
		csDTO.setNombre(centro.getNombre());
		csDTO.setDireccion(centro.getDireccion());
		csDTO.setNumVacunas(centro.getNumVacunas());
		csDTO.setfInicio(centro.getfInicio());
		csDTO.setfFin(centro.getfFin());
		csDTO.setFranja(centro.getFranja());
		csDTO.setCupo(centro.getCupo());
		assertEquals("Centro con id: "+centro.getId(),CentroCtrl.insertarCentro(csDTO));
		CentroCtrl.borrarCentro(csDTO);
	}

	@Then("se crea un centro de salud")
	public void se_crea_un_centro_de_salud() {
		
	}

}