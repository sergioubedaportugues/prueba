package uclm.grupo2.sigeva.model;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Usuario") 
public class Usuario{

    @Id
    private String id;
    private String login;
    private String password;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String dni;
    private String rol;
    private CentroSalud cs;
    private String nombreCentro;

    public Usuario() {
    	this.id=UUID.randomUUID().toString();
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha512Hex(password);
    }
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getNombreCentro() {
		return nombreCentro;
	}
	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}
	
	public CentroSalud getCs() {
		return cs;
	}
	public void setCs(CentroSalud cs) {
		this.cs = cs;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", password=" + password + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", telefono=" + telefono + ", dni=" + dni + ", rol=" + rol + "]";
	}

}