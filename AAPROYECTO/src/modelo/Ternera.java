package modelo;

public class Ternera extends Carne{

	/**
	 * Variable constante de oferta que se aplica cuando el stock es inferior a 5
	 */
	public static final double DESCUENTO = 0.10;

	private boolean joven;
	
	/**
	 * Constructor
	 * @param id
	 * @param nombre
	 * @param proveedor
	 * @param stock
	 * @param precioBase
	 * @param fecha
	 */
	
	public Ternera(String id, String nombre, String proveedor, int stock, double precioBase, String fecha, boolean j) {
		super(id, nombre, proveedor, stock, precioBase, fecha);
		this.joven = j;
	}

	@Override
	public double precioVenta() {
		return this.getStock()>5?this.getPrecioBase()
				:this.getPrecioBase()+(this.getPrecioBase()*DESCUENTO);
	}

	public boolean isJoven() {return joven;}
	public void setJoven(boolean joven) {this.joven = joven;}
	
	
	
}
