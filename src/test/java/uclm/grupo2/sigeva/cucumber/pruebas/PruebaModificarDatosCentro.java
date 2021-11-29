package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Given;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.CentroSaludDTO;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
import io.cucumber.java.en.Then;
public class PruebaModificarDatosCentro {
	

	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Given("los datos asociados {string}, {string}, {string}, {string}, {string}, {string} y {string}")
	public void los_datos_asociados_y(String nombreCentro, String direccionCentro, String numeroVacunas, String fInicio, String fFin, String franja, String cupo) {
		UsuarioDTO uDTO= new UsuarioDTO();
		
		Optional<Usuario> optUser = user.findByLogin("administrador");
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
		
		nombreCentro= "Miguelturra33"; direccionCentro="Avenida Parque 8"; numeroVacunas="7780"; fInicio = "09:30"; fFin = "14:00"; franja= "6"; cupo="5";
		String nombreCentroN= "Miguelturra34"; String direccionCentroN="Avenida Parque 6"; String numeroVacunasN="8780";
		
		CentroSalud centro = new CentroSalud();
		centro.setNombre(nombreCentro);
		centro.setDireccion(direccionCentro);
		centro.setNumVacunas(numeroVacunas);
		centro.setfInicio(fInicio);
		centro.setfFin(fFin);
		centro.setFranja(franja);
		centro.setCupo(cupo);
		
		CentroSaludDTO csDTO= new CentroSaludDTO();
		csDTO.setIdDTO(centro.getId());
		csDTO.setNombreDTO(centro.getNombre());
		csDTO.setDireccionDTO(centro.getDireccion());
		csDTO.setNumVacunasDTO(centro.getNumVacunas());
		csDTO.setfInicioDTO(centro.getfInicio());
		csDTO.setfFinDTO(centro.getfFin());
		csDTO.setFranjaDTO(centro.getFranja());
		csDTO.setCupoDTO(centro.getCupo());
		CentroCtrl.insertarCentro(csDTO);
		
		centro.setNombre(nombreCentroN);
		centro.setDireccion(direccionCentroN);
		centro.setNumVacunas(numeroVacunasN);
		csDTO.setNombreDTO(centro.getNombre());
		csDTO.setDireccionDTO(centro.getDireccion());
		csDTO.setNumVacunasDTO(centro.getNumVacunas());
		
		assertEquals("Centro modificado",CentroCtrl.modificarCentro(csDTO));
		CentroCtrl.borrarCentro(csDTO);
	}

	@Then("se ha modificado la informacion del centro correctamente")
	public void se_ha_modificado_la_informacion_del_centro_correctamente() {

	}
}
