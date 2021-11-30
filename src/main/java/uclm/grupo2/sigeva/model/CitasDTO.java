package uclm.grupo2.sigeva.model;


public class CitasDTO{


    private String id;
    private CentroSalud cs;
    private String horas;
    private Usuario paciente;
    private boolean aplicada;
    private int numCita;
    private String dia;

    

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

	public CentroSalud getCs() {
		return cs;
	}

	public void setCs(CentroSalud cs) {
		this.cs = cs;
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

	public boolean isAplicada() {
		return aplicada;
	}

	public void setAplicada(boolean aplicada) {
		this.aplicada = aplicada;
	}

	public int getNumCita() {
		return numCita;
	}

	public void setNumCita(int numCita) {
		this.numCita = numCita;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
	

	

}