package edu.uclm.esi.carreful.http;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.carreful.dao.CategoriaDao;
import edu.uclm.esi.carreful.dao.ProductDao;
import edu.uclm.esi.carreful.exceptions.CarrefulCamposVaciosException;
import edu.uclm.esi.carreful.exceptions.CarrefulDuplicatedException;
import edu.uclm.esi.carreful.exceptions.CarrefulElementNotFoundException;
import edu.uclm.esi.carreful.exceptions.CarrefulMinimumNumberException;
import edu.uclm.esi.carreful.exceptions.CarrefulNotNumericException;
import edu.uclm.esi.carreful.exceptions.CarrefulPriceException;
import edu.uclm.esi.carreful.model.Categoria;
import edu.uclm.esi.carreful.model.Product;

@RestController
@RequestMapping("menu")
public class EmployeeController extends CookiesController {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoriaDao categoriaDao;
	
	@PostMapping("/add")
	public void add(@RequestBody Product product) {
		try {
			Optional<Product> optProduct = productDao.findById(product.getIdProduct());
			if (optProduct.isPresent())
				throw new CarrefulDuplicatedException();
			else {
				if(product.getNombre().isEmpty())
					throw new CarrefulCamposVaciosException();
				if(product.getPrecio()==0)
					throw new CarrefulPriceException();
				if(Character.isDigit((char) product.getPrecio()))
					throw new CarrefulNotNumericException();
				if(product.getCodigo().isEmpty())
					throw new CarrefulCamposVaciosException();
				if(product.getStock()<0)
					throw new CarrefulMinimumNumberException();
				if(Character.isDigit(product.getStock()))
					throw new CarrefulNotNumericException();
				if(product.getCategoria().getIdCategoria().isEmpty())
					throw new CarrefulCamposVaciosException();
					
				productDao.save(product);
				Categoria categoria = product.getCategoria();
				categoria.setNumeroDeProductos(productDao.findByCategoria(product.getCategoria()).size());
				categoriaDao.save(categoria);
			}
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/modificarProducto")
	public void modificarProducto(@RequestBody Product product) {
		try {
			
			Optional<Product> optProduct = productDao.findById(product.getIdProduct());
			
			if (optProduct.isPresent()) {
				 	Product preProduct = optProduct.get();
	                preProduct.setNombre(product.getNombre());
	                preProduct.setPrecio(product.getPrecio());
	                preProduct.setCodigo(product.getCodigo());
	                preProduct.setStock(product.getStock());
	    			
	                Optional <Categoria> categoria = categoriaDao.findByNombre(product.getCategoria().getNombre());
	                
	                if (categoria.isPresent()) {
	                	preProduct.setCategoria(categoria.get());
	                }
	                else {
	                	throw new CarrefulElementNotFoundException();
	                }
	                preProduct.setPicture(product.getPicture());
	                productDao.save(preProduct);			
			}
			else
				throw new CarrefulElementNotFoundException();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@GetMapping("/getTodos")
	public List<Product> get() {
		try {
			return productDao.findAll();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/borrarProducto")
	public void borrarProducto(@RequestBody Product product) {
		try {
			Optional<Product> optProduct = productDao.findById(product.getIdProduct());
			if (optProduct.isPresent()){
				productDao.deleteById(product.getIdProduct());
				Categoria categoria = product.getCategoria();
				categoria.setNumeroDeProductos(productDao.findByCategoria(product.getCategoria()).size());
				categoriaDao.save(categoria);
			} 
			else
				throw new CarrefulElementNotFoundException();
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}