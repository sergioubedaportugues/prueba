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
		List <Citas> citasHostia = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		citasHostia.get(0).setAplicada(true);
		List <Citas> c = cita.getByPacienteOrderByNumCitaAsc(optUser.get());
		
		LocalDate date = LocalDate.parse(c.get(0).getDia(), formatterDia);
		date = date.minusDays(1);
		c.get(0).setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
		cita.save(c.get(0));
		LoginCtrl.cerrarSesion();
		
		
		Optional<Usuario> optUser2 = user.findByLogin("sanitario");
		uDTO2.setId(optUser2.get().getId());
		uDTO2.setLogin(optUser2.get().getLogin());
		uDTO2.setPassword(optUser2.get().getPassword());
		uDTO2.setNombre(optUser2.get().getNombre());
		uDTO2.setApellidos(optUser2.get().getApellidos());
		uDTO2.setTelefono(optUser2.get().getTelefono());
		uDTO2.setDni(optUser2.get().getDni());
		uDTO2.setRol(optUser2.get().getRol());
		uDTO2.setCs(optUser2.get().getCs());
		uDTO2.setDosis(optUser2.get().getDosis());
		LoginCtrl.iniciarSesion(uDTO2);
		
		CitasDTO cDTO= new CitasDTO();
		cDTO.setId(c.get(0).getId());
		cDTO.setHoras(c.get(0).getHoras());
		cDTO.setDia(c.get(0).getDia());
		cDTO.setPaciente(c.get(0).getPaciente());
		cDTO.setCs(c.get(0).getCs());
		cDTO.setNumCita(c.get(0).getNumCita());
		cDTO.setAplicada(c.get(0).isAplicada());
		
		
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
		uDTO3.setId(optUser3.get().getId());
		uDTO3.setLogin(optUser3.get().getLogin());
		uDTO3.setPassword(optUser3.get().getPassword());
		uDTO3.setNombre(optUser3.get().getNombre());
		uDTO3.setApellidos(optUser3.get().getApellidos());
		uDTO3.setTelefono(optUser3.get().getTelefono());
		uDTO3.setDni(optUser3.get().getDni());
		uDTO3.setRol(optUser3.get().getRol());
		uDTO3.setCs(optUser3.get().getCs());
		uDTO3.setDosis(optUser3.get().getDosis());
		
		LoginCtrl.iniciarSesion(uDTO3);	
		
		csDTO.setId(centro.getId());
		csDTO.setNombre(centro.getNombre());
		csDTO.setDireccion(centro.getDireccion());
		csDTO.setNumVacunas(centro.getNumVacunas());
		csDTO.setfInicio(centro.getfInicio());
		csDTO.setfFin(centro.getfFin());
		csDTO.setFranja(centro.getFranja());
		csDTO.setCupo(centro.getCupo());
		CentroCtrl.modificarCentro(csDTO);
		
		LoginCtrl.cerrarSesion();
	}

	@Then("se aplica una vacuna")
	public void se_aplica_una_vacuna() {
	}	
}
