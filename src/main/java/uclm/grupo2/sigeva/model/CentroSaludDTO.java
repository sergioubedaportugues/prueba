package uclm.grupo2.sigeva.model;

public class CentroSaludDTO{


    private String id;
    private String direccion;
    private String nombre;
    private String cupo;
    private String fInicio;
    private String fFin;
    private String numVacunas;
    private String franja;
    
    



	public CentroSaludDTO(CentroSalud cs) {
		this.id=cs.getId();
		this.nombre = cs.getNombre();
		this.direccion = cs.getDireccion();
		this.numVacunas = cs.getNumVacunas();
		this.fInicio = cs.getfInicio();
		this.fFin = cs.getfFin();
		this.franja = cs.getFranja();
		this.cupo = cs.getCupo();
		
	}
	public CentroSaludDTO() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCupo() {
		return cupo;
	}
	public void setCupo(String cupo) {
		this.cupo = cupo;
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
	public String getNumVacunas() {
		return numVacunas;
	}
	public void setNumVacunas(String numVacunas) {
		this.numVacunas = numVacunas;
	}
	public String getFranja() {
		return franja;
	}
	public void setFranja(String franja) {
		this.franja = franja;
	}
	

	

    
}