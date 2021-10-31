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
				if(cs.getNombre().isEmpty()||cs.getDireccion().isEmpty() || cs.getNumVacunas().isEmpty() ||cs.getfInicio().isEmpty() || cs.getfFin().isEmpty()   || cs.getCupo().isEmpty())
					throw new CamposVaciosException();
				if(!esNumericoEntero(cs.getNumVacunas()) || !esNumericoEntero(cs.getCupo()))
					throw new ValorNumericoException(); 
				if(Integer.parseInt(cs.getNumVacunas())<0 || Integer.parseInt(cs.getCupo())<0)
					throw new NumeroMinimoException();
				if((!validarHoras(cs.getfInicio()) || !tiempoHoras(cs.getfInicio()) || !validarHoras(cs.getfFin()) || !tiempoHoras(cs.getfFin())) || (!controlHoras(cs.getfInicio(), cs.getfFin()))  )
					throw new FormatoHoraException();
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

	public static boolean esNumericoEntero(String cadena) throws ValorNumericoException{
           try{
        	   boolean isNumeric =  cadena.matches("[+-]?\\d*(\\.\\d+)?");
        	   if(!isNumeric)
        		   return isNumeric;
               return isNumeric;
           } catch (Exception e) {
        	   throw new ValorNumericoException();
           }
	}

			
	private static boolean validarHoras(String hora) {
		if (hora.length() != 5)
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

	private static boolean controlHoras(String hInicio, String hFin) {
		String[] horaI = hInicio.split(":");
		String[] horaF = hFin.split(":");
		if (Integer.parseInt(horaI[0]) < Integer.parseInt(horaF[0])) {
			return true;
		} else {
			if (Integer.parseInt(horaI[0]) == Integer.parseInt(horaF[0]) && (Integer.parseInt(horaI[1]) < Integer.parseInt(horaF[1]))) {
				return true;
			}
		}
		return false;

	}
	private static boolean tiempoHoras(String horas) {
		boolean valido = false;
		String[] h = horas.split(":");
		if(Integer.parseInt(h[0])<24 && (Integer.parseInt(h[1])<60)) {
			valido = true;
		}
		return valido;
	}
}