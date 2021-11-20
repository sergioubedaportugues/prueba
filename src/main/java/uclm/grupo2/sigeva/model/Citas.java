package uclm.grupo2.sigeva.model;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Citas") 
public class Citas{

    @Id
    private String id;
    private String horas;
    private String dia;
    private Usuario paciente;
    private CentroSalud cs;
    private int numCita;
    private boolean aplicada;

	public Citas() {
    	this.id=UUID.randomUUID().toString();
    	this.setNumCita(0);
    	this.setAplicada(false);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public CentroSalud getCs() {
		return cs;
	}

	public void setCs(CentroSalud cs) {
		this.cs = cs;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getNumCita() {
		return numCita;
	}

	public void setNumCita(int numCita) {
		this.numCita = numCita;
	}

	public boolean isAplicada() {
		return aplicada;
	}

	public void setAplicada(boolean aplicada) {
		this.aplicada = aplicada;
	}

}