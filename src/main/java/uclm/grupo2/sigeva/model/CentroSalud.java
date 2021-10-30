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

    private String fInicio;
    private String fFin;
    
    private int cupo;
    //se debe meter horario

    public CentroSalud() {
    	this.id=UUID.randomUUID().toString();
    }
    
	public CentroSalud(String nombre, String direccion, String num_vacunas, String fInicio, String fFin) {
		super();
		this.id = UUID.randomUUID().toString();;
		this.nombre = nombre;
		this.direccion = direccion;
		this.num_vacunas = num_vacunas;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.cupo = 3;
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


	public String getfInicio() {
		return fInicio;
	}



	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}



	public String getfFin() {
		return fFin;
	}



	public void setfFin(String fFin) {
		this.fFin = fFin;
	}



	public int getCupo() {
		return cupo;
	}



	public void setCupo(int cupo) {
		this.cupo = cupo;
	}



	@Override
	public String toString() {
		return "CentroSalud [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", num_vacunas="
				+ num_vacunas + ", fInicio=" + fInicio + ", fFin=" + fFin + ", cupo=" + cupo + "]";
	}
    
}