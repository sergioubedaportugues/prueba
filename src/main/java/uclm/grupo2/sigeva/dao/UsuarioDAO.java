package uclm.grupo2.sigeva.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.grupo2.sigeva.model.CentroSalud;
import uclm.grupo2.sigeva.model.Usuario;


@Repository
public interface UsuarioDAO extends MongoRepository<Usuario,String>{

	Optional<Usuario> findByLogin(String login);
	List<Usuario> getByRol(String rol);
	List<Usuario> getByLogin(String login);
	List<Usuario> getByCs(CentroSalud cs);
	

}
