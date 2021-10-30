package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import uclm.grupo2.sigeva.dao.CentroSaludDAO;
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;

import uclm.grupo2.sigeva.exceptions.CentroDuplicadoException;
import uclm.grupo2.sigeva.exceptions.NumeroMinimoException;
import uclm.grupo2.sigeva.exceptions.ValorNumericoException;

import uclm.grupo2.sigeva.exceptions.FormatoHinicioHfinException;
import uclm.grupo2.sigeva.exceptions.FormatoHoraException;
import uclm.grupo2.sigeva.model.CentroSalud;

@RestController
@RequestMapping("gestionCentroSalud")
public class CentroSaludController {

	@Autowired
	private CentroSaludDAO center;

	@PostMapping("/insertCenter")
	public String insertarCentro(@RequestBody CentroSalud cs) {
		try {
			Optional<CentroSalud> optCenter = center.findByNombre(cs.getNombre());
			if (optCenter.isPresent())

				throw new CentroDuplicadoException();
			else {
				if(cs.getNombre().isEmpty())
					throw new CamposVaciosException();
				if(cs.getDireccion().isEmpty())
					throw new CamposVaciosException();
				if(cs.getNum_vacunas().isEmpty())
					throw new CamposVaciosException();
				if(!esNumericoEntero(cs.getNum_vacunas()))
					throw new ValorNumericoException(); 
				if(Integer.parseInt(cs.getNum_vacunas())<0)
					throw new NumeroMinimoException(); 
					
				center.save(cs);

				}
			
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
		return "Centro con id: "+cs.getId();
	}
	@GetMapping("/findAllCenters")
	public List<CentroSalud> getCentros(){
		return center.findAll();
	}
	@GetMapping("/findAllCenters/{id}")
	public Optional<CentroSalud> getCentro(@PathVariable String id){
		return center.findById(id);
	}
	/*@DeleteMapping("/delete/{id}")
	public String deleteUsuarios(@PathVariable String id) {
		user.deleteById(id);
		return "Usuario eliminado con id: "+id;
	}*/
	
	public static boolean esNumericoEntero(String cadena) throws ValorNumericoException{
           try{
        	   Integer.parseInt(cadena);
               return true;

           } catch (Exception e) {
        	   throw new ValorNumericoException();
           }
	}

			
	private static boolean validarHoras(String hora) throws FormatoHoraException {
		if (hora.length() != 6)
			return false;
		for (int i = 0; i < hora.length() - 1; i++) {
			if (i == 2) {
				if (hora.charAt(i) != ':')
					return false;
			} else {
				if (!Character.isDigit(hora.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean controlHoras(String hInicio, String hFin) throws FormatoHinicioHfinException {
		String[] horaI = hInicio.split(":");
		String[] horaF = hFin.split(":");
		if (Integer.parseInt(horaI[0]) < Integer.parseInt(horaF[0])) {
			return true;
		} else {
			if (Integer.parseInt(horaI[0]) == Integer.parseInt(horaF[0])) {
				if (Integer.parseInt(horaI[1]) < Integer.parseInt(horaF[1])) {
					return true;
				}

			}
			return false;
		}

	}
	private static boolean tiempoHoras(String horas) throws FormatoHoraException {
		String[] h = horas.split(":");
		if(Integer.parseInt(h[0])<24 && (Integer.parseInt(h[1])<60)) {
			return true;
		}else
			return false;
	}

}