package uclm.grupo2.sigeva.model;


public class CitasDTO{


    private String id;
    private String horas;
    private String dia;
    private Usuario paciente;
    private CentroSalud cs;
    private int numCita;
    private boolean aplicada;

    

	public CitasDTO(Citas c) {
		this.id=c.getId();
		this.horas = c.getHoras();
		this.dia = c.getDia();
		this.paciente = c.getPaciente();
		this.cs = c.getCs();
		this.numCita = c.getNumCita();
		this.aplicada =c.isAplicada();
	}

	public CitasDTO() {
   
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

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
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