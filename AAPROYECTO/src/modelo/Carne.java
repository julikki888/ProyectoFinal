package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Carne implements Comparable<Carne>{
	
	/**
	 * Clase carne, variables Nombre, idProducto, Stock,Precio_base,Proveedor
	 * El toString, queremos que se vea como: 
	 * 			C1 - Cerdo al ajillo - Stock - Precio_Venta - Proveedor
	 * habra un metodo PrecioVenta
	 */
	/**
	 * Variables de clase
	 */
	public static final int POLLO=25, CERDO=15, TERNERA=10;
	
	/**
	 * Variables de Instancia
	 */
	private String id,nombre,proveedor;
	private int stock;
	private double precioBase;

	public Carne(String id, String nombre, String proveedor, int stock, double precioBase) {
		this.id = id;
		this.nombre = nombre;
		this.proveedor = proveedor;
		this.stock = stock;
		this.precioBase = precioBase;
		
	}

	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}	
	public String getProveedor() {return proveedor;}
	public void setProveedor(String proveedor) {this.proveedor = proveedor;}
	public int getStock() {return stock;}
	public void setStock(int stock) {this.stock = stock;}
	public double getPrecioBase() {return precioBase;}
	public void setPrecioBase(double precioBase) {this.precioBase = precioBase;}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carne other = (Carne) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(Carne o) {
		return this.id.compareToIgnoreCase(o.id);
	}
	
	public double precioVenta() {
		if(this.id.charAt(0)=='C') {
			return this.getStock()>5?this.getPrecioBase()
					:this.getPrecioBase()+(this.getPrecioBase()*CERDO);
		}
		if(this.id.charAt(0)=='T') {
			return this.getStock()>5?this.getPrecioBase()
					:this.getPrecioBase()+(this.getPrecioBase()*TERNERA);
		}
		return this.getStock()>5?this.getPrecioBase()
				:this.getPrecioBase()+(this.getPrecioBase()*POLLO);
	}
	
	
	@Override
	public String toString() {
		return this.id + " - " + this.nombre + " - " + this.stock + " - " + this.precioBase
				+ " - " + this.proveedor;
	}
	
	

}
