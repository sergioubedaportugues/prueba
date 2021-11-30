package uclm.grupo2.sigeva.model;

public class UsuarioDTO{

    private String id;
    private String login;
    private String apellidos;
    private String telefono;
    private String password;
    private String nombre;
    private String dni;
    private int dosis;
    private String rol;
    private CentroSalud cs;
   

    public UsuarioDTO(Usuario u) {
    	this.id=u.getId();
    	this.login=u.getLogin();
    	this.password=u.getPassword();
    	this.nombre=u.getNombre();
    	this.apellidos=u.getApellidos();
    	this.telefono=u.getTelefono();
    	this.dni=u.getDni();
    	this.rol=u.getRol();
    	this.cs=u.getCs();
    	this.dosis=u.getDosis();
    	
    	}
    public UsuarioDTO() {
	
	}
	public String getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public int getDosis() {
		return dosis;
	}
	public void setDosis(int dosis) {
		this.dosis = dosis;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public CentroSalud getCs() {
		return cs;
	}
	public void setCs(CentroSalud cs) {
		this.cs = cs;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}