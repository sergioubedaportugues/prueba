package uclm.grupo2.sigeva.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uclm.grupo2.sigeva.model.Token;

@Repository
public interface TokenDAO extends MongoRepository<Token,String>{
	List<Token> findByLogin();
}
