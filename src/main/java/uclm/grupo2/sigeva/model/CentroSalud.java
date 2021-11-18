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
    private String numVacunas;

    private String fInicio;
    private String fFin;
    private String franja;
    private String cupo;
    

    public CentroSalud() {
    	this.id=UUID.randomUUID().toString();
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

	public String getNumVacunas() {
		return numVacunas;
	}

	public void setNumVacunas(String numVacunas) {
		this.numVacunas = numVacunas;
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



	public String getCupo() {
		return cupo;
	}



	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	public String getFranja() {
		return franja;
	}

	public void setFranja(String franja) {
		this.franja = franja;
	}

    
}