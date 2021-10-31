package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("gestionCitas")
public class CitasController {

	@Autowired
	private CitasDAO cita;
	
	@Autowired
	private CentroSaludDAO center;
	
	@Autowired
	private UsuarioDAO user;
	
	private static final String DDMMAA = "dd-MM-yyyy";
	private static final String HHMM = "HH:mm";
	
	@PostMapping("/insertCita")
	public void insertarCita() {
		try {
			boolean insertada = false;
			
			List<CentroSalud> centros = center.findAll();
			CentroSalud random = centros.get(new Random().nextInt(centros.size()));

			List<Usuario> pacientes = user.getByRol("Paciente");
			Usuario ramon = pacientes.get(new Random().nextInt(pacientes.size()));
			
			Citas citaNueva = new Citas();
			Citas segundaCita = new Citas();
			citaNueva.setCs(random);
			
			LocalDate date = LocalDate.now();
			date = date.plusDays(1); // Para que de la cita al día siguiente de pedirla
			LocalTime time = LocalTime.now();
			
			
			citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
			citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
			citaNueva.setNombreCentro(random.getNombre());
			citaNueva.setPaciente(ramon);
			segundaCita.setCs(citaNueva.getCs());
			segundaCita.setNombreCentro(citaNueva.getNombreCentro());
			segundaCita.setPaciente(citaNueva.getPaciente());
			

			int vueltas = 0;
			
			while (!insertada) {
				
				if(cita.getByDiaAndNombreCentroAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs().getNombre(), citaNueva.getHoras().substring(0,2)).size() < Integer.parseInt(citaNueva.getCs().getCupo()) && LocalTime.parse(citaNueva.getHoras()).compareTo(LocalTime.parse(citaNueva.getCs().getfFin())) < 0 &&  LocalTime.parse(citaNueva.getHoras()).compareTo(LocalTime.parse(citaNueva.getCs().getfInicio())) > 0 && Integer.parseInt(citaNueva.getCs().getNumVacunas()) >= 2) {

					
					int vacunasDisponibles = Integer.parseInt(citaNueva.getCs().getNumVacunas());
					vacunasDisponibles = vacunasDisponibles - 2;
					if(vacunasDisponibles<10) {
						citaNueva.getCs().setNumVacunas(Integer.toString(100));
					} else {
						citaNueva.getCs().setNumVacunas(Integer.toString(vacunasDisponibles));
					}
					center.save(citaNueva.getCs());
					cita.save(citaNueva);
					date = date.plusDays(1);
					segundaCita.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
					segundaCita.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
					cita.save(segundaCita);
					
					insertada = true;
				} else {
					if(vueltas == 24) {
						date = date.plusDays(1);
						citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
						vueltas = 0;
					}
					time = time.plusHours(1);
					citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
					vueltas++;
				}
			}
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}			
	}
	
	@GetMapping("/findAllCitas")
	public List<Citas> getCitas(){
		return cita.findAll();
	}
	
	
	@GetMapping("/findAllCitas/{id}")
	public Optional<Citas> getCita(@PathVariable String id){
		return cita.findById(id);
	}
	
	@GetMapping("/mostrarCitasPedidas")
	public List<Citas> mostrarCitasPedidas(){		
		List<Citas> misCitas = cita.findAll();
		if (misCitas.size()>2) {
			for(int i=0; i<misCitas.size()-2;i++) {
				misCitas.remove(i);
				i--;
			}
		}
		return misCitas;
	}
	
}