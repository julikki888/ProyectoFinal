package modelo;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Carne implements Comparable<Carne>{
	
	/**
	 * Clase carne, padre de clases Cerdo, Ternera, Pollo
	 * Heredaran las variables Nombre, idProducto, Stock,Precio_base,Proveedor
	 * El toString lo modificas aqui, queremos que se vea como: 
	 * 			C1 - Cerdo al ajillo - Stock - Precio_Venta - Proveedor
	 * Habra una clase abstracta Precio_venta 
	 */
	
	/**
	 * Variables de Instancia
	 */
	private String id,nombre,proveedor;
	private int stock;
	private double precioBase;
	private LocalDate fecha;

	public Carne(String id, String nombre, String proveedor, int stock, double precioBase, String fecha) {
		this.id = id;
		this.nombre = nombre;
		this.proveedor = proveedor;
		this.stock = stock;
		this.precioBase = precioBase;
		if (LibFechas8.isFechaCorrecta(fecha)) {
			this.fecha = LibFechas8.convierteStringToLocalDate(fecha);
		}else {
			this.fecha = LocalDate.now();

		}
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
	public LocalDate getFecha() {return fecha;}
	public void setFecha(LocalDate fecha) {this.fecha = fecha;}

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
	
	public abstract double precioVenta();
	
	
	@Override
	public String toString() {
		return this.id + " - " + this.nombre + " - " + this.stock + " - " + this.precioBase
				+ " - " + this.proveedor;
	}
	
	

}
