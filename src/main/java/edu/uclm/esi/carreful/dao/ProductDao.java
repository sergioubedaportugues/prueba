package edu.uclm.esi.carreful.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.carreful.model.Categoria;
import edu.uclm.esi.carreful.model.Product;


@Repository
public interface ProductDao extends JpaRepository <Product, String> {
	
	List<Product> findByCategoria(Categoria categoria);
}
