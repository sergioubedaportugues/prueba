package uclm.grupo2.sigeva.model;


public class CitasDTO{


    private String idDTO;
    private String horasDTO;
    private String diaDTO;
    private Usuario pacienteDTO;
    private CentroSalud csDTO;
    private int numCitaDTO;
    private boolean aplicadaDTO;

    

	public CitasDTO(Citas c) {
		this.idDTO=c.getId();
		this.horasDTO = c.getHoras();
		this.diaDTO = c.getDia();
		this.pacienteDTO = c.getPaciente();
		this.csDTO = c.getCs();
		this.numCitaDTO = c.getNumCita();
		this.aplicadaDTO =c.isAplicada();
	}

	public CitasDTO() {
   
    }

	public String getIdDTO() {
		return idDTO;
	}

	public void setIdDTO(String idDTO) {
		this.idDTO = idDTO;
	}

	public String getHorasDTO() {
		return horasDTO;
	}

	public void setHorasDTO(String horasDTO) {
		this.horasDTO = horasDTO;
	}

	public String getDiaDTO() {
		return diaDTO;
	}

	public void setDiaDTO(String diaDTO) {
		this.diaDTO = diaDTO;
	}

	public Usuario getPacienteDTO() {
		return pacienteDTO;
	}

	public void setPacienteDTO(Usuario pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public CentroSalud getCsDTO() {
		return csDTO;
	}

	public void setCsDTO(CentroSalud csDTO) {
		this.csDTO = csDTO;
	}

	public int getNumCitaDTO() {
		return numCitaDTO;
	}

	public void setNumCitaDTO(int numCitaDTO) {
		this.numCitaDTO = numCitaDTO;
	}

	public boolean isAplicadaDTO() {
		return aplicadaDTO;
	}

	public void setAplicadaDTO(boolean aplicadaDTO) {
		this.aplicadaDTO = aplicadaDTO;
	}

	

}