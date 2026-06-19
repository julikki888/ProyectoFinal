package controlador;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.*;
import vista.VistaCliente;
import vista.VistaGestor;
import vista.VistaInicio;

public class Controlador implements ActionListener{
	VistaInicio miVistaInicio;
	VistaCliente miVistaCliente;
	VistaGestor miVistaGestor;
	private DAOProductos miDAOP;
	private DAOClientes miDAOC;


	public Controlador(VistaInicio v1,VistaCliente v2,VistaGestor v3) throws SQLException, MiExcepcion {
		this.miVistaInicio = v1;
		this.miVistaCliente = v2;
		this.miVistaGestor = v3;
		try {
			this.miDAOP = new DAOProductos();
			this.miDAOC = new DAOClientes();
		}
	catch (SQLException e) {
		System.out.println(e.getMessage());
		JOptionPane.showMessageDialog(miVistaInicio, "Problema SQL");
		System.exit(0);
	}
	catch (NullPointerException e) {
		JOptionPane.showMessageDialog(miVistaInicio, "Problema al cargar la BD");
		System.exit(0);
	}
	catch (Exception e) {
		JOptionPane.showMessageDialog(miVistaInicio, "El programa se debe reiniciar");
		e.printStackTrace();
	}

		/**
		 * Carga de los productos en lso contenedores correspondientes inicial
		 */
		cargaComboBox();
		cargaTable("^[CTP].*");
		cargaTFProductos();
	}



	@Override
	public void actionPerformed(ActionEvent e){
		/**
		 * Botones VistaInicio
		 */
		if(e.getSource()==miVistaInicio.getbIniciar()) {
			miVistaInicio.setVisible(false);
			miVistaCliente.setVisible(true);
			try {
				miVistaCliente.getlCliente().setText(extraeNombre());
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==miVistaInicio.getbGestion()) {
			miVistaInicio.setVisible(false);
			miVistaGestor.setVisible(true);
		}
		////////////////////////////////////////////////////////////
		/**
		 * Botones Vista Cliente
		 */
		
		if(e.getSource()==miVistaCliente.getbRegresar()) {
			miVistaCliente.setVisible(false);
			miVistaInicio.setVisible(true);
		}
		
		if(e.getSource()==miVistaCliente.getbInfo()) {
			try {
				JOptionPane.showMessageDialog(miVistaCliente,
						miDAOP.buscaCodigo(
								((String)miVistaCliente.getModeloCbProductos()
										.getSelectedItem()).split("-")[0]));
			} catch (HeadlessException | NumberFormatException | SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}//fin boton info
		
		/**
		 * Añadir compra
		 */
		if(e.getSource()==miVistaCliente.getbAñadirCarrito()) {
			
			String[] array = new String[6];
			String[] auxArray = ((String)miVistaCliente.getModeloCbProductos().getSelectedItem()).split("-");
			array[0]=auxArray[0];
			array[1]=auxArray[1];
			array[2]=miVistaCliente.getCbUnidades().getSelectedItem()+"";
			array[3]=auxArray[3];
			array[4]=auxArray[4];
			try {
				array[5]=miDAOP.buscaCodigo(array[0]).precioVenta()+"";
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
			
			miVistaCliente.getModeloTablaCompras();
			auxArray[2]=array[2];
					
			miVistaCliente.getModeloTablaCompras().addRow(array);
			try {
				cargaTable("^[CTP].*");
			} catch (SQLException | MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		
		/**
		 * Finalizar Compra
		 */
		if(e.getSource()==miVistaCliente.getbFinalizarCompra()) {
			
		}
		
		/**
		 * radio buttons
		 */
		if(e.getSource()==miVistaCliente.getRbTodas()) {
			try {
				cargaTable("^[CTP].*");
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==miVistaCliente.getRbCerdo()) {
			try {
				cargaTable("^C.*");
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==miVistaCliente.getRbTernera()) {
			try {
				cargaTable("^T.*");
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==miVistaCliente.getRbPollo()) {
			try {
				cargaTable("^P.*");
			} catch (SQLException | MiExcepcion e1) {
				e1.printStackTrace();
			}
		}
		
		/**
		 * Botones del panel cliente
		 */
		if(e.getSource()==miVistaCliente.getbHistorialCliente()) {
			
		}
		
		/////////////////////////////////////////////////////////////
		/**
		 * Botones vista gestor
		 */
		
		if(e.getSource()==miVistaGestor.getbGuardar() &&
				miVistaGestor.getbGuardar().getText().equalsIgnoreCase("Regresar")) {
			miVistaGestor.setVisible(false);
			miVistaInicio.setVisible(true);
		}
		if (e.getSource()==miVistaGestor.getbNuevo())
			procesoNuevo();
		else if (e.getSource()==miVistaGestor.getbGuardar()&&(
				miVistaGestor.getbGuardar().getText().equals("Guardar")||
				miVistaGestor.getbGuardar().getText().equals("Guardar cambios")))
			try {
				procesoGuardar();
			} catch (MiExcepcion e1) {
				e1.printStackTrace();
			}
		else if (e.getSource()==miVistaGestor.getbCancelar())
			procesoCancelar();
		else if (e.getSource()==miVistaGestor.getbModificar())
			procesoModificar();
		else if (e.getSource()==miVistaGestor.getbEliminar())
			try {
				procesoEliminar();
			} catch (SQLException | MiExcepcion e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		if(e.getSource()==miVistaGestor.getCbProductos()) {
			cargaTFProductos();
		}
	}

	/**
	 * Metodo para extraer el nombre del string del combobox de clientes del inicio 
	 * @return
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	private String extraeNombre() throws SQLException, MiExcepcion{
		
		return miDAOC.buscaCodigo(Integer.parseInt(String.valueOf(
				((String) miVistaInicio.getModelocbClientes().getSelectedItem()).charAt(0)))).getNombre();
	}
	
	/**
	 * Metodo para cargar los text field de la Vista Gestor con losç
	 * 	 datos del producto seleccionado en el combo box
	 */
	private void cargaTFProductos() {
		if(miVistaGestor.getModelocbProductos().getSelectedItem()==null)
			return;
		String[] array = ((String)miVistaGestor.getModelocbProductos().getSelectedItem()).split("-");
		miVistaGestor.getTfCodigo().setText(array[0]);
		miVistaGestor.getTfNombre().setText(array[1]);
		miVistaGestor.getTfProveedor().setText(array[4]);
		miVistaGestor.getTfStock().setText(array[2]);
		miVistaGestor.getTfPrecio().setText(array[3]);
	}
	
	/**
	 * Metodo para cargar los comboBox	
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	private void cargaComboBox() throws SQLException, MiExcepcion{
		miVistaCliente.getModeloCbProductos().removeAllElements();
		miVistaGestor.getModelocbProductos().removeAllElements(); 
		miVistaInicio.getModelocbClientes().removeAllElements();

		for (Carne c : miDAOP.getAll()) {
			miVistaCliente.getModeloCbProductos().addElement(c.toString());
			
			miVistaGestor.getModelocbProductos().addElement(c.toString());
		}
		for (Cliente c : miDAOC.getAll()) {
			miVistaInicio.getModelocbClientes().addElement(c.toString());
		}
	}
	/**
	 * Metodo para cargar la tabla de los productos
	 * @throws SQLException
	 * @throws MiExcepcion
	 */
	private void cargaTable(String regex) throws SQLException, MiExcepcion{
		String[] array = new String[6];
		String[] auxArray; 
		miVistaCliente.getModeloTablaCatalogo().setRowCount(0);
		
		for (Carne c : miDAOP.getAll()) {
			auxArray =  c.toString().split("-");
			if(auxArray[0].matches(regex)) {
				for (int i = 0; i < auxArray.length; i++) {
					array[i]=auxArray[i];
				}
				array[5]=c.precioVenta() +"";
				miVistaCliente.getModeloTablaCatalogo().addRow(array);
			}
		}
		
	}
	
	private int buscarTabla(String datoBuscado) {
		// El dato que estás buscando
		boolean sw = false;
		int filaEncontrada = 0;
	
		var modelo = miVistaCliente.getModeloTablaCatalogo();
	
		// Recorremos únicamente las filas
		for (int i = 0; i < modelo.getRowCount() && sw==true; i++) {
		    
		    // Accedemos fijando la columna en 0
		    String codigo = (String)modelo.getValueAt(i, 0);
		    
		    
		    String textoCelda = codigo.trim();
		        
		        // Comparamos si coincide con el dato
		    if (textoCelda.equalsIgnoreCase(datoBuscado)) { 
		    	sw = true;
		    	filaEncontrada = i;
		        sw=false;		    	 
		        }
		    }
		return filaEncontrada;
	}
	
	
	/**
	 * Método que permite añadir un nuevo producto
	 */
	private void procesoNuevo() {
		
		// Habilitar/Deshabilitar los botones de operaciones
		miVistaGestor.getbNuevo().setEnabled(false);
		miVistaGestor.getbGuardar().setEnabled(true);
		miVistaGestor.getbModificar().setEnabled(false);
		miVistaGestor.getbEliminar().setEnabled(false);
		miVistaGestor.getbCancelar().setEnabled(true);
		
		// Cambiar el texto al boton "Guardar" 
		miVistaGestor.getbGuardar().setText("Guardar");
		
		activacionEntradaDatos(true);
		limpiarEntradaDatos();
	}

	
	/**
	 * Limpia los textField de entrada de datos
	 */
	public void limpiarEntradaDatos()
	{
		miVistaGestor.getTfCodigo().setText("");
		miVistaGestor.getTfProveedor().setText("");
		miVistaGestor.getTfNombre().setText("");
		miVistaGestor.getTfStock().setText("");
		miVistaGestor.getTfPrecio().setText("");
	}
	
	/**
	 * Habilita o deshabilita los campos de texto de entrada de datos
	 */
	public void activacionEntradaDatos(boolean estado)
	{
		miVistaGestor.getTfCodigo().setEditable(estado);
		miVistaGestor.getTfNombre().setEditable(estado);
		miVistaGestor.getTfProveedor().setEditable(estado);
		miVistaGestor.getTfStock().setEditable(estado);
		miVistaGestor.getTfPrecio().setEditable(estado);
	}
	
	/**
	 * Este metodo permitira guardar o modificar un producto
	 * @throws MiExcepcion 
	 */
	private void procesoGuardar() throws MiExcepcion {
		try {
			// Crearnos un objeto tipo Carne con los datos que hay en el formulario
			Carne miCarne = new Carne(
					miVistaGestor.getTfCodigo().getText(),
					miVistaGestor.getTfNombre().getText(),
					miVistaGestor.getTfProveedor().getText(),
					Integer.parseInt(miVistaGestor.getTfStock().getText().trim()),
					Double.parseDouble(miVistaGestor.getTfPrecio().getText()));
			
			if (miVistaGestor.getbGuardar().getText().equals("Guardar")) { // Alta del nuevo Producto
				miDAOP.insertaCarne(miCarne);
				JOptionPane.showMessageDialog(miVistaGestor,"Producto almacenado");
				cargaComboBox();
				cargaTable("^[CTP].*");
			}
			else {  // Modificación del producto
				miDAOP.modificaCarne(miCarne);
				JOptionPane.showMessageDialog(miVistaGestor,"Producto modificado");
				cargaComboBox();
				cargaTable("^[CTP].*");
			}
			
			procesoCancelar(); 
		}
		catch (SQLException e) {
			//miModelo.printSQLException(e);
			JOptionPane.showMessageDialog(miVistaGestor, "Error al grabar los datos, puede\n"
											     + "que los datos ya existan.\n"
											     + "Compruebe que el código del producto no exista.");
			try { // Si se produce una excepcion cierra la consulta
				miDAOP.crearConsulta();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(miVistaGestor, "Datos erróneos");
		}

	}

	/**
	 * Este metodo se encarga de dejar el formulario como estaba al principio
	 */
	private void procesoCancelar() {
		
		// Habilitar/Deshabilitar los botones de operaciones
		miVistaGestor.getbNuevo().setEnabled(true);
		miVistaGestor.getbGuardar().setEnabled(true);
		miVistaGestor.getbModificar().setEnabled(true);
		miVistaGestor.getbEliminar().setEnabled(true);
		miVistaGestor.getbCancelar().setEnabled(true);
		
		// Cambiar el texto al boton "Regresar" por si tenia "Guardar Cambios"
		miVistaGestor.getbGuardar().setText("Regresar");
		
		activacionEntradaDatos(false);
	}
	
	/**
	 * Método que prepara la apliacion para modificar
	 */
	private void procesoModificar() {
		
		// Habilitar/Deshabilitar los botones de operaciones
		miVistaGestor.getbNuevo().setEnabled(false);
		miVistaGestor.getbGuardar().setEnabled(true);
		miVistaGestor.getbModificar().setEnabled(false);
		miVistaGestor.getbEliminar().setEnabled(false);
		miVistaGestor.getbCancelar().setEnabled(true);
		activacionEntradaDatos(true);
		
		miVistaGestor.getTfCodigo().setEditable(false);  // Poner no editable el codigo
		miVistaGestor.getbGuardar().setText("Guardar cambios");
		
	}
	
	/**
	 * Método que permite porder eliminar un producto
	 * @throws MiExcepcion 
	 * @throws SQLException 
	 */
	private void procesoEliminar() throws SQLException, MiExcepcion {
		
		int resp = JOptionPane.showConfirmDialog(
				miVistaGestor, 
				"¿Está seguro de querer elimnar el producto actual?");
		if (resp==JOptionPane.YES_OPTION) {
			try {
				miDAOP.eliminaProducto(miVistaGestor.getTfCodigo().getText());
				JOptionPane.showMessageDialog(miVistaGestor, "El producto ha sido borrado");
				procesoCancelar();
			}
			catch(SQLException e) {
			//	miModelo.printSQLException(e);
				JOptionPane.showMessageDialog(miVistaGestor, "Error al borrar los datos");
			}
		}
		else {
			JOptionPane.showMessageDialog(miVistaGestor, "Eliminación cancelada");
		}
		cargaComboBox();
		cargaTable("^[CTP].*");
	}
	
}
