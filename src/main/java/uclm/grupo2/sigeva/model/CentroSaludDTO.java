package uclm.grupo2.sigeva.model;

public class CentroSaludDTO{


    private String idDTO;
    private String nombreDTO;
    private String direccionDTO;
    private String numVacunasDTO;

    private String fInicioDTO;
    private String fFinDTO;
    private String franjaDTO;
    private String cupoDTO;
    



	public CentroSaludDTO(CentroSalud cs) {
		this.idDTO=cs.getId();
		this.nombreDTO = cs.getNombre();
		this.direccionDTO = cs.getDireccion();
		this.numVacunasDTO = cs.getNumVacunas();
		this.fInicioDTO = cs.getfInicio();
		this.fFinDTO = cs.getfFin();
		this.franjaDTO = cs.getFranja();
		this.cupoDTO = cs.getCupo();
		
	}
	public CentroSaludDTO() {
		
	}
	public String getIdDTO() {
		return idDTO;
	}
	public void setIdDTO(String idDTO) {
		this.idDTO = idDTO;
	}
	public String getNombreDTO() {
		return nombreDTO;
	}
	public void setNombreDTO(String nombreDTO) {
		this.nombreDTO = nombreDTO;
	}
	public String getDireccionDTO() {
		return direccionDTO;
	}
	public void setDireccionDTO(String direccionDTO) {
		this.direccionDTO = direccionDTO;
	}
	public String getNumVacunasDTO() {
		return numVacunasDTO;
	}
	public void setNumVacunasDTO(String numVacunasDTO) {
		this.numVacunasDTO = numVacunasDTO;
	}
	public String getfInicioDTO() {
		return fInicioDTO;
	}
	public void setfInicioDTO(String fInicioDTO) {
		this.fInicioDTO = fInicioDTO;
	}
	public String getfFinDTO() {
		return fFinDTO;
	}
	public void setfFinDTO(String fFinDTO) {
		this.fFinDTO = fFinDTO;
	}
	public String getFranjaDTO() {
		return franjaDTO;
	}
	public void setFranjaDTO(String franjaDTO) {
		this.franjaDTO = franjaDTO;
	}
	public String getCupoDTO() {
		return cupoDTO;
	}
	public void setCupoDTO(String cupoDTO) {
		this.cupoDTO = cupoDTO;
	}

	

    
}