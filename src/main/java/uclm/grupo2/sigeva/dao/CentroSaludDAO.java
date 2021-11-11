package uclm.grupo2.sigeva.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uclm.grupo2.sigeva.model.CentroSalud;


@Repository
public interface CentroSaludDAO extends MongoRepository<CentroSalud,String>{

	List<CentroSalud> findByNombre(String nombre);
}