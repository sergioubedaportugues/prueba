package uclm.grupo2.sigeva.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Citas;


@Repository
public interface CitasDAO extends MongoRepository<Citas,String>{

	List<Citas> findByCs(CentroSalud cs);
	List<Citas> getByDia(String dia);
	List<Citas> findByHoras(String horas);
	List<Citas> getByDiaAndHoras(String dia, String horas);
	List<Citas> getByDiaAndNombreCentro(String dia, String nombreCentro);
	List<Citas> searchByHorasStartingWith(String horas);
	List<Citas> getByDiaAndNombreCentroAndHorasStartingWith(String dia, String nombreCentro, String horas);
	
}
