package uclm.grupo2.sigeva.http;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.UsuarioInexistenteException;
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
	public String insertarCita() {
		try {
			boolean insertada = false;
			
			List<Usuario> pacientes = user.getByRol("Paciente");
			Usuario paciente = pacientes.get(new Random().nextInt(pacientes.size()));
			
			List<CentroSalud> centros = center.findByNombre(paciente.getCs().getNombre());
			CentroSalud cs = centros.get(0);
			
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			
			int vueltasCita = 0;
			int vueltasCitaSeg = 0;
			
			if(paciente.getDosis()==0 || cita.getByPaciente(paciente).isEmpty()) {
				Citas citaNueva = new Citas();
				Citas segundaCita = new Citas();
				citaNueva.setCs(cs);

				date = date.plusDays(1);

				citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
				citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
				citaNueva.setPaciente(paciente);
				citaNueva.setNumCita(1);
				segundaCita.setCs(citaNueva.getCs());
				segundaCita.setPaciente(citaNueva.getPaciente());
				segundaCita.setNumCita(2);

				

				while (!insertada) {

					if (cita.getByDiaAndCsAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs(),
							citaNueva.getHoras().substring(0, 2)).size() < Integer.parseInt(citaNueva.getCs().getCupo())
							&& LocalTime.parse(citaNueva.getHoras())
									.compareTo(LocalTime.parse(citaNueva.getCs().getfFin())) < 0
							&& LocalTime.parse(citaNueva.getHoras())
									.compareTo(LocalTime.parse(citaNueva.getCs().getfInicio())) > 0) {

						cita.save(citaNueva);
						date = date.plusDays(21);
						segundaCita.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
						segundaCita.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
						
						if (cita.getByDiaAndCsAndHorasStartingWith(segundaCita.getDia(), segundaCita.getCs(),
								segundaCita.getHoras().substring(0, 2)).size() < Integer.parseInt(segundaCita.getCs().getCupo())
								&& LocalTime.parse(segundaCita.getHoras())
										.compareTo(LocalTime.parse(segundaCita.getCs().getfFin())) < 0
								&& LocalTime.parse(segundaCita.getHoras())
										.compareTo(LocalTime.parse(segundaCita.getCs().getfInicio())) > 0) {
							cita.save(segundaCita);
							insertada = true;
						} else {
							if (vueltasCitaSeg == 24) {
								date = date.plusDays(1);
								segundaCita.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
								vueltasCitaSeg = 0;
							}
							time = time.plusHours(1);
							segundaCita.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
							vueltasCitaSeg++;
						}

					} else {
						if (vueltasCita == 24) {
							date = date.plusDays(1);
							citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
							vueltasCita = 0;
						}
						time = time.plusHours(1);
						citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
						vueltasCita++;
					}
				}

			} else if (paciente.getDosis()==1 || cita.getByPaciente(paciente).size()==1) {
				Citas citaNueva = new Citas();
				citaNueva.setCs(cs);
				
				citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
				citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
				citaNueva.setPaciente(paciente);
				citaNueva.setNumCita(2);
				
				while (!insertada) {
					if (cita.getByDiaAndCsAndHorasStartingWith(citaNueva.getDia(), citaNueva.getCs(),
							citaNueva.getHoras().substring(0, 2)).size() < Integer.parseInt(citaNueva.getCs().getCupo())
							&& LocalTime.parse(citaNueva.getHoras())
									.compareTo(LocalTime.parse(citaNueva.getCs().getfFin())) < 0
							&& LocalTime.parse(citaNueva.getHoras())
									.compareTo(LocalTime.parse(citaNueva.getCs().getfInicio())) > 0) {

						cita.save(citaNueva);
					} else {
						if (vueltasCita == 24) {
							date = date.plusDays(1);
							citaNueva.setDia(date.format(DateTimeFormatter.ofPattern(DDMMAA)));
							vueltasCita = 0;
						}
						time = time.plusHours(1);
						citaNueva.setHoras(time.format(DateTimeFormatter.ofPattern(HHMM)));
						vueltasCita++;
					}
				}
				
			}
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}	
		return "Cita creada";
	}
	
	@DeleteMapping("/deleteCita")
	public void borrarCita(@RequestBody Citas c) {
		try {
			Optional<Citas> optCita = cita.findById(c.getId());
			if (optCita.isPresent()) {
				
				cita.deleteById(c.getId());
			} else
				throw new UsuarioInexistenteException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/findAllCitas")
	public List<Citas> getCitas(){
		return cita.findAll();
	}
	
	
	@GetMapping("/findAllCitas/{id}")
	public List<Citas> getCita(@PathVariable String id){
		return cita.getById(id);
		
	}
	
	@GetMapping("/mostrarCitasPedidas")
	public List<Citas> mostrarCitasPedidas(){
		List<Citas> misCitas = cita.findAll();
		if (misCitas.size()>2) {
			for(int i= misCitas.size()-3; i>=0 ;i--) {
				misCitas.remove(i);
			}
		}
		return misCitas;
	}
}