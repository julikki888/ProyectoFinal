package modelo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreaCargaTablaCarne {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/tienda";
		String usuario = "admin";
		String clave   = ")hIFhcueidRbpEc/";

		try (Connection conexion = DriverManager.getConnection(url, usuario, clave)){
				
			System.out.println("Conexion establecida");
			
			// Metodo que crea la tabla disco
			creaTablaProductos(conexion);
			
			// Metodo que carga datos en la tabla disco a partir de un fichero
			cargaTablaProductos(conexion);
		}
		catch (SQLException e) {
			printSQLException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void creaTablaProductos(Connection con) throws SQLException
	{
		String creaTabla = "create table tienda.productos " +
	            "(codigo VARCHAR(10) NOT NULL PRIMARY KEY, " +
		        "producto VARCHAR(50) NOT NULL, " +
	            "proveedor VARCHAR(50) NOT NULL, " +
		        "stock INT, " +
	            "precio_base DOUBLE)";
		
		System.out.println("Se va a ejecutar: "+creaTabla);
		
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate(creaTabla);
			System.out.println("Tabla productos creada");
		}
		
	}

	public static void cargaTablaProductos(Connection con) throws SQLException {

		try (Scanner sc = new Scanner(Path.of("./files/productos.txt"),StandardCharsets.UTF_8)){
			
			while (sc.hasNextLine()) {

				String [] arrayDatos = sc.nextLine().split("-");

				String sqlString = "INSERT INTO productos VALUES (?,?,?,?,?)";
				
				try (PreparedStatement ps = con.prepareStatement(sqlString)) {

					ps.setString(1, arrayDatos[0]); // Código
					ps.setString(2, arrayDatos[1]); // Producto
					ps.setString(3, arrayDatos[2]); // Proveedor
					ps.setInt(4, Integer.parseInt(arrayDatos[3]));//Stock
					ps.setDouble(5, Double.parseDouble(arrayDatos[4])); // Precio_Base

					ps.executeUpdate();
				}
			}
			System.out.println("Productos volcados desde fichero correctamente");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método que muestra una descripcion completa de la excepcion
	 * que se ha producido
	 * @param ex -- Excepcion SQL generada
	 */
	public static void printSQLException(SQLException ex) {
		
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
