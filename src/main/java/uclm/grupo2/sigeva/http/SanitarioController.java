package uclm.grupo2.sigeva.http;

import java.util.List;
import java.util.Optional;

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
import uclm.grupo2.sigeva.exceptions.CamposVaciosException;
import uclm.grupo2.sigeva.exceptions.CentroConCitasException;
import uclm.grupo2.sigeva.exceptions.CentroDuplicadoException;
import uclm.grupo2.sigeva.exceptions.CentroInexistenteException;
import uclm.grupo2.sigeva.exceptions.NumeroMinimoException;
import uclm.grupo2.sigeva.exceptions.ValorNumericoException;


import uclm.grupo2.sigeva.exceptions.FormatoHoraException;
import uclm.grupo2.sigeva.model.CentroSalud;

@RestController
@RequestMapping("vistaSanitario")
public class SanitarioController {
	
	@Autowired
	private CitasDAO cita;

	// ConsultarCitas
	// AplicarVacuna
}