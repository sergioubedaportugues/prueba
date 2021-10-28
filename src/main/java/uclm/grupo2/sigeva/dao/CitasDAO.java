package uclm.grupo2.sigeva.dao;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.grupo2.sigeva.model.Citas;


@Repository
public interface CitasDAO extends MongoRepository<Citas,String>{

	Optional<Citas> findByCs(String Cs);

}