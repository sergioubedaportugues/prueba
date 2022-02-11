package edu.uclm.esi.carreful.http;

import java.util.List;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.carreful.dao.CategoriaDao;
import edu.uclm.esi.carreful.dao.ProductDao;
import edu.uclm.esi.carreful.exceptions.CarrefulElementNotFoundException;
import edu.uclm.esi.carreful.model.Carrito;
import edu.uclm.esi.carreful.model.Categoria;
import edu.uclm.esi.carreful.model.Product;


@RestController
@RequestMapping("product")
public class ProductController extends CookiesController {
	
	private static final String CAR = "carrito";  
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoriaDao categoriaDao;

	@GetMapping("/getTodos")
	public List<Product> get() {
		try {
			return productDao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCategoriaProducto/{idCategoria}")
	public List<Product> getCategoriaProducto(@PathVariable String idCategoria) {
		try {
			Categoria categoria = categoriaDao.findByIdCategoria(idCategoria);
			return productDao.findByCategoria(categoria);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@PostMapping("/addAlCarrito/{idProduct}")
	public Carrito addAlCarrito(HttpServletRequest request, @PathVariable String idProduct) {
		try {
			Carrito carrito = (Carrito) request.getSession().getAttribute(CAR);
			if (carrito == null) {
				carrito = new Carrito();
				request.getSession().setAttribute(CAR, carrito);
			}
			Product producto = productDao.findById(idProduct).get();
			carrito.add(producto, 1);
			return carrito;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/borrarProducto/{idProduct}")
	public void borrarProducto(@PathVariable String idProduct) {
		try {
			Optional<Product> optProduct = productDao.findById(idProduct);
			if (optProduct.isPresent())
				productDao.deleteById(idProduct);

			else
				throw new CarrefulElementNotFoundException();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/borrarCarrito/{idProduct}")
	public Carrito borrarCarrito(HttpServletRequest request, @PathVariable String idProduct) {
		Carrito carrito = (Carrito) request.getSession().getAttribute(CAR);
		Product producto = productDao.findById(idProduct).get();
		carrito.delete(producto, 1);
		return carrito;
	}
}