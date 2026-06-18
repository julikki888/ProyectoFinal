package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa el patrón DAO con la tabla productos, este patrón ofrece 
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

public class DAOClientes{

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
	public DAOClientes() throws ClassNotFoundException, SQLException {
		
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
		String sqlString = "SELECT * FROM CLIENTES";
		
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
	public Cliente crearCliente() throws MiExcepcion, SQLException {
		return new Cliente(
					rsNavegar.getInt("codigo"), 
					rsNavegar.getString("dni"),
					rsNavegar.getString("nombre"),
					rsNavegar.getString("direccion"),
					rsNavegar.getString("telefono"));
	}
	

	
	/**
	 * Metodo que devuelve una coleccion con todos los productos que hay en la tabla productos
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public List<Cliente> getAll() throws SQLException, MiExcepcion {
	
		rsNavegar.beforeFirst(); // Para posicionar la consulta al principio
		
		List<Cliente> listaClientes = new ArrayList<>();

		while (rsNavegar.next()) {
			listaClientes.add(crearCliente());
		}

		rsNavegar.beforeFirst();
		
		return listaClientes;
	}
	
	
	/**
	 * Método que busca un Cliente cuyo codigo coincida con el indicado
	 * @param cod
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	public Cliente buscaCodigo(int cod) throws SQLException, MiExcepcion {

		PreparedStatement ps = con.prepareStatement("SELECT * FROM clientes WHERE codigo = ?");
		ps.setInt(1, cod);

		ResultSet rs = ps.executeQuery();

		Cliente clienteBuscado = null;

		if (rs.next()) {
			clienteBuscado =
					new Cliente(
							rs.getInt("codigo"),
							rs.getString("dni"),
							rs.getString("nombre"),
							rs.getString("direccion"),
							rs.getString("telefono"));
		}

		rs.close();
		ps.close();

		return clienteBuscado;

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

