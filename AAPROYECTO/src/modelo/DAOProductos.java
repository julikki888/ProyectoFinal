package modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;


/**
 * Esta clase implementa el patrón DAO con la tabla Discos, este patrón ofrece 
 * operaciones para interactuar con la base de datos. 
 * 
 * Se encarga de realizar todas las operaciones CRUD es el acrónimo de 
 * "Crear, Leer, Actualizar y Borrar" (del original en inglés: Create, 
 * Read, Update and Delete), que se usa para referirse a las funciones 
 * básicas en bases de datos o la capa de persistencia en un software.
 * 
 * Lo que este patrón pretende principalmente es independizar la aplicación
 * de la forma de acceder a la base de datos.
 * 
 */

public class DAOProductos {

	/**
	 * Variables de instancia
	 */
	private Connection con;   // Objeto con la conexión a la BD
	private Statement stmt;   // Objeto que permite ejecutar sentencias SQL
	private ResultSet rsNavegar; // Resultado de la consulta para navegar por las filas de la tabal
	
	
	/**
	 * Constructor
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public DAOProductos() throws ClassNotFoundException, SQLException {
		
		this.estableceConexion();  // Dar valor a la variable con (Connection)
		
		this.crearStatement();  // Dar valor a la variable stmt (Statement)
		
		this.crearConsulta();  // Dar valor a la variable rsNavegar (ResultSet)
		
	}

	/**
	 * Método que establece la conexión con la BD 
	 * (Os dejo dos posibles plantamientos, los dos son igual de válidos)
	 * @throws MiExcepcion 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void estableceConexion() throws ClassNotFoundException, SQLException {
		
		this.con = BDConnection.getConnection();
		System.out.println("Conexión establecida");
	}	
	
	/**
	 * Crear el objeto Statement, el cual nos va a permitir ejecutar instrucciones SQL
	 * @throws SQLException 
	 */
	public void crearStatement() throws SQLException {
		
		this.stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,  // Indica que el cursor es bidireccional
						                                  // y que refleja los cambios en la BD
						ResultSet.CONCUR_UPDATABLE);  // Los datos del ResultSet son actualizables 
	}
	
	/**
	 * Método que permite crear la consulta SQL para la navegación
	 * entre los distintas filas devueltas por la selección
	 *  
	 * @throws SQLException 
	 */
	public void crearConsulta() throws SQLException {
		String sqlString = "SELECT * FROM DISCOS";
		
		this.rsNavegar = stmt.executeQuery(sqlString);
	}
	
	/**
	 * Método que cierra la conexión
	 * @throws SQLException
	 */
	public void cierraConexion() throws SQLException {
		con.close();
		System.out.println("Conexión cerrada");
	}
	

	/**
	 * Método que cierra el Statement
	 * @throws SQLException 
	 */
	public void cierraStatement() throws SQLException {
		stmt.close();
	}
	
	
	/**
	 * Método que recoge una fila o tupla de la tabla de resultados (ResultSet) 
	 * de la consulta, y devuelve con esos datos un objeto tipo Carne
	 * 
	 * Los datos que recoge, son los de la "tupla" en la que se encuentre posicionado
	 * rsNavegar en ese momento
	 * 
	 * @return
	 * @throws SQLException 
	 * @throws MiExcepcion 
	 */
	public Carne crearCarne() throws MiExcepcion, SQLException {
		return new Carne(
					rsNavegar.getString("codigo"), 
					rsNavegar.getString("producto"),
					rsNavegar.getString("proveedor"),
					rsNavegar.getInt("stock"),
					rsNavegar.getDouble("precio_base"));
	}
	
	/**
	 * Metodo que permite añadir un nuevo producto (tupla) a la BD
	 * @param ob -- producto que se va a añadir
	 * @throws SQLException
	 */
	public void insertaCarne(Carne ob) throws SQLException {
		
		PreparedStatement ps = 
				con.prepareStatement("insert into tienda.productos values (?,?,?,?,?)");

		ps.setString(1,ob.getId());
		ps.setString(2, ob.getNombre());
		ps.setString(3, ob.getProveedor());
		ps.setInt(4, ob.getStock());
		ps.setDouble(5,ob.getPrecioBase());
		
		ps.executeUpdate();
		ps.close();
	
		this.crearConsulta();  // Actualizar el resultSet de navegación con el nuevo producto
	}
	
	
	/**
	 * Metodo que permite modificar un producto existente en la talba productos, el producto
	 * con los datos modificados llega como parametro, se puede modificar todo
	 * excepto el codigo del producto
	 */
	public void modificaCarne(Carne ob) throws SQLException {
		
		PreparedStatement ps = con.prepareStatement(
				"UPDATE productos SET producto = ?, proveedor = ?, stock = ?, precio_base = ? WHERE codigo = ?");
		
		ps.setString(1,ob.getId());
		ps.setString(2, ob.getNombre());
		ps.setString(3, ob.getProveedor());
		ps.setInt(4, ob.getStock());
		ps.setDouble(5,ob.getPrecioBase());
		
		ps.executeUpdate();
		ps.close();
		
		// Volver a crear la consulta, para que se actualicen los datos en el resultSet de navegacion
		this.crearConsulta();		
	}
	
	
	/**
	 * Metodo que permite borrar el producto cuyo código coincide con el que nos
	 * llega como parámetro
	 */
	public void eliminaProducto(int cod) throws SQLException{
		PreparedStatement ps = 
				con.prepareStatement("DELETE FROM productos WHERE codigo = ?");
		
		ps.setInt(1, cod);

		ps.executeUpdate();
		ps.close();

		// Volver a crear la consulta, para actualizar los datos del resultset de navegación
		this.crearConsulta();
	}

	
	/**
	 * Metodo que devuelve una coleccion con todos los productos que hay en la tabla productos
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public List<Carne> getAll() throws SQLException, MiExcepcion {
	
		rsNavegar.beforeFirst(); // Para posicionar la consulta al principio
		
		List<Carne> listaDiscos = new ArrayList<>();

		while (rsNavegar.next()) {
			listaDiscos.add(crearCarne());
		}

		rsNavegar.beforeFirst();
		
		return listaDiscos;
	}
	
	
	/**
	 * Método que busca un producto cuyo codigo coincida con el indicado
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public Carne buscaCodigo(int cod) throws SQLException, MiExcepcion {

		PreparedStatement ps = con.prepareStatement("SELECT * FROM productos WHERE codigo = ?");
		ps.setInt(1, cod);

		ResultSet rs = ps.executeQuery();

		Carne productoBuscado = null;

		if (rs.next()) {
			productoBuscado = 
					new Carne(
							rsNavegar.getString("codigo"), 
							rsNavegar.getString("producto"),
							rsNavegar.getString("proveedor"),
							rsNavegar.getInt("stock"),
							rsNavegar.getDouble("precio_base"));
		}

		rs.close();
		ps.close();

		return productoBuscado;

	}
	
	/**
	 * Método que muestra una descripcion completa de la excepcion
	 * que se ha producido
	 * @param ex -- Excepcion SQL generada
	 */
	public void printSQLException(SQLException ex) {
		
		ex.printStackTrace(System.err);
		System.err.println("SQLState: "+ex.getSQLState());
		System.err.println("Error code: "+ex.getErrorCode());
		System.err.println("Message: "+ex.getMessage());
		Throwable t = ex.getCause();
		while (t!=null) {
			System.out.println("Cause: "+t);
			t = t.getCause();
		}
	}
}

