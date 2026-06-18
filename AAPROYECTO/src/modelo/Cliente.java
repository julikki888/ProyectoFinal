package modelo;

public class Cliente {
/**
 * 	// Código
	// dni
	// nombre
	// direccion
	// telefono 
 */
	/**
	 * Variables de instancia
	 */
	private String dni, nombre, direccion, telefono;
	private int codigo;

public Cliente(int codigo, String dni, String nombre, String direccion, String telefono) {
	this.codigo = codigo;
	this.dni = dni;
	this.nombre = nombre;
	this.direccion = direccion;
	this.telefono = telefono;
}


@Override
	public String toString() {
		return this.codigo + " - " + this.dni + " - " + this.nombre + " - " + this.direccion + " - "
				+ this.telefono;
	}


public int getCodigo() {return codigo;}
public void setCodigo(int codigo) {this.codigo = codigo;}
public String getDni() {return dni;}
public void setDni(String dni) {this.dni = dni;}
public String getNombre() {return nombre;}
public void setNombre(String nombre) {this.nombre = nombre;}
public String getDireccion() {return direccion;}
public void setDireccion(String direccion) {this.direccion = direccion;}
public String getTelefono() {return telefono;}
public void setTelefono(String telefono) {this.telefono = telefono;}
	
	
	
}
