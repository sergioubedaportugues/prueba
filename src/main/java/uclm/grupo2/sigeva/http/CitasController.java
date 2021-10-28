package uclm.grupo2.sigeva.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CitasDAO;
import uclm.grupo2.sigeva.dao.UsuarioDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.FormatoDniException;
import uclm.grupo2.sigeva.exceptions.NoEsTelefonoException;
import uclm.grupo2.sigeva.exceptions.NumeroMinimoException;
import uclm.grupo2.sigeva.exceptions.RolInvalidoException;
import uclm.grupo2.sigeva.exceptions.UsuarioDuplicadoException;
import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;
import uclm.grupo2.sigeva.model.Usuario;

@RestController
@RequestMapping("gestionCitas")
public class CitasController {

	@Autowired
	private CitasDAO cita;
	
	
	@PostMapping("/insertCita")
	public String insertarCita() {
			Citas pruebaMaxima = new Citas();
		
			CentroSalud cs = new CentroSalud("Hola", "hola", "20");
			pruebaMaxima.setCs(cs);
			pruebaMaxima.setDia("28-05");
			pruebaMaxima.setHoras("17:00");
			
			cita.save(pruebaMaxima);

			
		return "Centro con id: "+pruebaMaxima.getId();
	}
	
	@GetMapping("/findCita") // O ALGO ASI
	public String findCita() {
		
		
		return null;
			
	}
	@GetMapping("/findAllCitas")
	public List<Citas> getCitas(){
		return cita.findAll();
	}
	@GetMapping("/findAllCitas/{id}")
	public Optional<Citas> getCita(@PathVariable String id){
		return cita.findById(id);
	}
	/*@DeleteMapping("/delete/{id}")
	public String deleteUsuarios(@PathVariable String id) {
		user.deleteById(id);
		return "Usuario eliminado con id: "+id;
	}*/
	
}