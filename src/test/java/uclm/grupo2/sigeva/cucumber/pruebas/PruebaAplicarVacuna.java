package uclm.grupo2.sigeva.cucumber.pruebas;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.DiaDistintoVacunaException;
import uclm.grupo2.sigeva.exceptions.StockInsuficienteException;
import uclm.grupo2.sigeva.exceptions.TokenBorradoException;
import uclm.grupo2.sigeva.exceptions.VacunaAplicadaException;
import uclm.grupo2.sigeva.http.CentroSaludController;
import uclm.grupo2.sigeva.http.CitasController;
import uclm.grupo2.sigeva.http.LoginController;
import uclm.grupo2.sigeva.http.SanitarioController;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.CentroSaludDTO;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.CitasDTO;
import uclm.grupo2.sigeva.model.Usuario;
import uclm.grupo2.sigeva.model.UsuarioDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PruebaAplicarVacuna {
	
	@Autowired
	private UsuarioDAO user;
	
	@Autowired
	private LoginController LoginCtrl;
	
	@Autowired
	private CitasController CitasCtrl;
	
	@Autowired
	private SanitarioController SanitarioCtrl;
	
	@Autowired
	private CentroSaludController CentroCtrl;
	
	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static DateTimeFormatter formatterDia = DateTimeFormatter.ofPattern(DDMMAA);
	
	@Given("un usuario sanitario loggeado")
	public void un_usuario_sanitario_loggeado() throws TokenBorradoException, DiaDistintoVacunaException, StockInsuficienteException, VacunaAplicadaException {
		
		UsuarioDTO uDTO= new UsuarioDTO();
		UsuarioDTO uDTO2= new UsuarioDTO();
		UsuarioDTO uDTO3= new UsuarioDTO();
		

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
		CitasCtrl.insertarCita();
		List <Citas> citasHostia = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		citasHostia.get(0).setAplicada(true);
		List <Citas> c = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		LocalDate date = LocalDate.parse(c.get(0).getDia(), formatterDia);
		date = date.minusDays(1);
		c.get(0).setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		cita.save(c.get(0));
		LoginCtrl.cerrarSesion();
		
		
		Optional<Usuario> optUser2 = user.findByLogin("sanitario");
		uDTO2.setIdDTO(optUser2.get().getId());
		uDTO2.setLoginDTO(optUser2.get().getLogin());
		uDTO2.setPasswordDTO(optUser2.get().getPassword());
		uDTO2.setNombreDTO(optUser2.get().getNombre());
		uDTO2.setApellidosDTO(optUser2.get().getApellidos());
		uDTO2.setTelefonoDTO(optUser2.get().getTelefono());
		uDTO2.setDniDTO(optUser2.get().getDni());
		uDTO2.setRolDTO(optUser2.get().getRol());
		uDTO2.setCsDTO(optUser2.get().getCs());
		uDTO2.setDosisDTO(optUser2.get().getDosis());
		LoginCtrl.iniciarSesion(uDTO2);
		
		CitasDTO cDTO= new CitasDTO();
		cDTO.setIdDTO(c.get(0).getId());
		cDTO.setHorasDTO(c.get(0).getHoras());
		cDTO.setDiaDTO(c.get(0).getDia());
		cDTO.setPacienteDTO(c.get(0).getPaciente());
		cDTO.setCsDTO(c.get(0).getCs());
		cDTO.setNumCitaDTO(c.get(0).getNumCita());
		cDTO.setAplicadaDTO(c.get(0).isAplicada());
		
		
		assertEquals("Vacuna aplicada.",SanitarioCtrl.aplicarVacuna(cDTO));
		cita.deleteAll(citasHostia);
		CentroSaludDTO csDTO= new CentroSaludDTO();
		CentroSalud centro = optUser2.get().getCs();
		
		int vacunaAumentada = Integer.parseInt(centro.getNumVacunas());
		centro.setNumVacunas(String.valueOf(vacunaAumentada));
		optUser.get().setDosis(0);
		user.save(optUser.get());

		LoginCtrl.cerrarSesion();
		
		Optional<Usuario> optUser3 = user.findByLogin("administrador");
		uDTO3.setIdDTO(optUser3.get().getId());
		uDTO3.setLoginDTO(optUser3.get().getLogin());
		uDTO3.setPasswordDTO(optUser3.get().getPassword());
		uDTO3.setNombreDTO(optUser3.get().getNombre());
		uDTO3.setApellidosDTO(optUser3.get().getApellidos());
		uDTO3.setTelefonoDTO(optUser3.get().getTelefono());
		uDTO3.setDniDTO(optUser3.get().getDni());
		uDTO3.setRolDTO(optUser3.get().getRol());
		uDTO3.setCsDTO(optUser3.get().getCs());
		uDTO3.setDosisDTO(optUser3.get().getDosis());
		
		LoginCtrl.iniciarSesion(uDTO3);	
		
		csDTO.setIdDTO(centro.getId());
		csDTO.setNombreDTO(centro.getNombre());
		csDTO.setDireccionDTO(centro.getDireccion());
		csDTO.setNumVacunasDTO(centro.getNumVacunas());
		csDTO.setfInicioDTO(centro.getfInicio());
		csDTO.setfFinDTO(centro.getfFin());
		csDTO.setFranjaDTO(centro.getFranja());
		csDTO.setCupoDTO(centro.getCupo());
		CentroCtrl.modificarCentro(csDTO);
		
		LoginCtrl.cerrarSesion();
	}

	@Then("se aplica una vacuna")
	public void se_aplica_una_vacuna() {
	}	
}
