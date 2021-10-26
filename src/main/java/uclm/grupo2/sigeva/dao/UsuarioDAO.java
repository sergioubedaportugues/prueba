package uclm.grupo2.sigeva.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.grupo2.sigeva.model.Usuario;


@Repository
public interface UsuarioDAO extends MongoRepository<Usuario,String>{

}
