package modelo;

public class Pollo extends Carne{

	/**
	 * Variable constante de oferta que se aplica cuando el stock es inferior a 5
	 */
	public static final double DESCUENTO = 0.25;
	
	
	/**
	 * Constructor
	 * @param id
	 * @param nombre
	 * @param proveedor
	 * @param stock
	 * @param precioBase
	 * @param fecha
	 */
	
	public Pollo(String id, String nombre, String proveedor, int stock, double precioBase, String fecha) {
		super(id, nombre, proveedor, stock, precioBase, fecha);
		
	}

	@Override
	public double precioVenta() {
		return this.getStock()>5?this.getPrecioBase()
				:this.getPrecioBase()+(this.getPrecioBase()*DESCUENTO);
	}
}
