package uclm.grupo2.sigeva.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Token") 
public class Token{

    @Id
    private String id;
    private String login;

    public Token() {
    	this.id=UUID.randomUUID().toString();
    	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
    
}