package uclm.grupo2.sigeva.model;

public class UsuarioDTO{

    private String idDTO;
    private String loginDTO;
    private String passwordDTO;
    private String nombreDTO;
    private String apellidosDTO;
    private String telefonoDTO;
    private String dniDTO;
    private String rolDTO;
    private CentroSalud csDTO;
    private int dosisDTO;

    public UsuarioDTO(Usuario u) {
    	this.idDTO=u.getId();
    	this.loginDTO=u.getLogin();
    	this.passwordDTO=u.getPassword();
    	this.nombreDTO=u.getNombre();
    	this.apellidosDTO=u.getApellidos();
    	this.telefonoDTO=u.getTelefono();
    	this.dniDTO=u.getDni();
    	this.rolDTO=u.getRol();
    	this.csDTO=u.getCs();
    	this.dosisDTO=u.getDosis();
    	
    	}
    public UsuarioDTO() {
	
	}
	public String getIdDTO() {
		return idDTO;
	}
	public void setIdDTO(String idDTO) {
		this.idDTO = idDTO;
	}
	public String getLoginDTO() {
		return loginDTO;
	}
	public void setLoginDTO(String loginDTO) {
		this.loginDTO = loginDTO;
	}
	public String getPasswordDTO() {
		return passwordDTO;
	}
	public void setPasswordDTO(String passwordDTO) {
		this.passwordDTO = passwordDTO;
	}
	public String getNombreDTO() {
		return nombreDTO;
	}
	public void setNombreDTO(String nombreDTO) {
		this.nombreDTO = nombreDTO;
	}
	public String getApellidosDTO() {
		return apellidosDTO;
	}
	public void setApellidosDTO(String apellidosDTO) {
		this.apellidosDTO = apellidosDTO;
	}
	public String getTelefonoDTO() {
		return telefonoDTO;
	}
	public void setTelefonoDTO(String telefonoDTO) {
		this.telefonoDTO = telefonoDTO;
	}
	public String getDniDTO() {
		return dniDTO;
	}
	public void setDniDTO(String dniDTO) {
		this.dniDTO = dniDTO;
	}
	public String getRolDTO() {
		return rolDTO;
	}
	public void setRolDTO(String rolDTO) {
		this.rolDTO = rolDTO;
	}
	public CentroSalud getCsDTO() {
		return csDTO;
	}
	public void setCsDTO(CentroSalud csDTO) {
		this.csDTO = csDTO;
	}
	public int getDosisDTO() {
		return dosisDTO;
	}
	public void setDosisDTO(int dosisDTO) {
		this.dosisDTO = dosisDTO;
	}
	
}