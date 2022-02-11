package edu.uclm.esi.carreful.http;

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

import edu.uclm.esi.carreful.dao.CategoriaDao;
import edu.uclm.esi.carreful.exceptions.CarrefulCamposVaciosException;
import edu.uclm.esi.carreful.exceptions.CarrefulDuplicatedException;
import edu.uclm.esi.carreful.exceptions.CarrefulElementNotFoundException;
import edu.uclm.esi.carreful.model.Categoria;


@RestController
@RequestMapping("categoria")
public class CategoriaController extends CookiesController {

	@Autowired
	private CategoriaDao categoriaDao;


	@PostMapping("/addCategoria")
	public void addCategoria(@RequestBody Categoria categoria) {
		try {
			Optional<Categoria> optCategoria = categoriaDao.findById(categoria.getIdCategoria());
			if (optCategoria.isPresent())
				throw new CarrefulDuplicatedException();
			else {
				if (((categoria.getNombre()).isEmpty())) {
					throw new CarrefulCamposVaciosException();
				}
				else
					categoriaDao.save(categoria);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping("/modificarCategoria")
	public void modificarCategoria(@RequestBody Categoria categoria) {
		try {
			Optional<Categoria> optCategoria = categoriaDao.findById(categoria.getIdCategoria());
			if (optCategoria.isPresent()) {
				Categoria preCategoria = optCategoria.get();
				preCategoria.setNombre(categoria.getNombre());
				preCategoria.setNumeroDeProductos(categoria.getNumeroDeProductos());
				categoriaDao.save(preCategoria);
			} else
				throw new CarrefulElementNotFoundException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@GetMapping("/getCategorias")
	public List<Categoria> get() {
		try {
			return categoriaDao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/borrarCategoria/{idCategoria}")
	public void borrarProducto(@PathVariable String idCategoria) {
		try {
			Optional<Categoria> optCategoria = categoriaDao.findById(idCategoria);
			if (optCategoria.isPresent())
				categoriaDao.deleteById(idCategoria);
			else
				throw new CarrefulElementNotFoundException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	
	
}