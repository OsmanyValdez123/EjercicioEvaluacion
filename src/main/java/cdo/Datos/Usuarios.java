package cdo.Datos;

public class Usuarios {
	private int id;
	private String nombre;
	private String email;
	private String genero;
	private int estatus;
	
	
	
	public Usuarios() {
		super();
		this.id = 1;
		this.nombre = "Javier";
		this.email = "javier@prueba.com";
		this.genero = "Masculino";
		this.estatus = 200;
	}

	public Usuarios(int id, String nombre, String email, String genero, int estatus) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.genero = genero;
		this.estatus = estatus;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombre=" + nombre + ", email=" + email + ", genero=" + genero + ", estatus="
				+ estatus + "]";
	}
	

}