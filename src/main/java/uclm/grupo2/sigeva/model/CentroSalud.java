package uclm.grupo2.sigeva.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CentroSalud") 
public class CentroSalud{

    @Id
    private String id;
    private String nombre;
    private String direccion;
    private String num_vacunas;
    //se debe meter horario

    public CentroSalud() {
    	this.id=UUID.randomUUID().toString();
    }
    
    

	public CentroSalud(String nombre, String direccion, String num_vacunas) {
		super();
		this.id = UUID.randomUUID().toString();;
		this.nombre = nombre;
		this.direccion = direccion;
		this.num_vacunas = num_vacunas;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNum_vacunas() {
		return num_vacunas;
	}

	public void setNum_vacunas(String num_vacunas) {
		this.num_vacunas = num_vacunas;
	}

	@Override
	public String toString() {
		return "CentroSalud [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", num_vacunas="
				+ num_vacunas + "]";
	}
    
}