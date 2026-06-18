package controlador;

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

		
		cargaComboBox();
		cargaTable();
	}


	private void cargaComboBox() throws SQLException, MiExcepcion{
		for (Carne c : miDAOP.getAll()) {
			miVistaCliente.getModeloCbProductos().addElement(c.toString());
			miVistaGestor.getModelocbProductos().addElement(c.toString());
		}
		for (Cliente c : miDAOC.getAll()) {
			miVistaInicio.getModelocbClientes().addElement(c.toString());
		}
	}
	
	private void cargaTable() throws SQLException, MiExcepcion{
		String[] array = new String[6];
		String[] auxArray; 
		
		for (Carne c : miDAOP.getAll()) {
			auxArray =  c.toString().split("-");
			for (int i = 0; i < auxArray.length; i++) {
				array[i]=auxArray[i];
			}
			array[5]=c.precioVenta() +"";
			miVistaCliente.getModeloTablaCatalogo().addRow(array);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		
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
		
		if(e.getSource()==miVistaCliente.getbRegresar()) {
			miVistaCliente.setVisible(false);
			miVistaInicio.setVisible(true);
		}
		
		if(e.getSource()==miVistaGestor.getbGuardar() &&
				miVistaGestor.getbGuardar().getText().equalsIgnoreCase("Regresar")) {
			miVistaGestor.setVisible(false);
			miVistaInicio.setVisible(true);
		}
		
		/*
		if(e.getSource()==miVista.getbInfo()) {
			JOptionPane.showMessageDialog(miVista,
					miVivero.buscarPlanta(miVista.getModeloCbPlantas()
							.getSelectedItem().toString().split(" ")[0]).toString());
		}//fin boton info
		
		if(e.getSource()==miVista.getbAñadirCarrito()) {
			try {
				Planta p = miVivero.buscarPlanta(miVista.getModeloCbPlantas()
						.getSelectedItem().toString().split(" ")[0]);
				String[] array = new String[5];
				array[0] = p.getIdPlanta();
				array[1] = p.getNombreComun();
				array[2] = miVista.getCbUnidades().getSelectedItem().toString();
				array[3] = Double.toString(p.getPrecioBase());
				array[4] = Double.toString(p.calculaPrecioVenta());	
				
				if(p.getStock()<Integer.parseInt(array[2])) {
					throw new Exception(Double.toString(p.getStock()));
				}
				
				miVista.getModeloTablaCompras().addRow(array);
				miVivero.ventaPlantas(p.getIdPlanta(),Integer.parseInt(array[2]));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(miVista, "Error: Stock insuficiente. "
						+ "Solo quedan " + e1.getMessage() + " unidades.");
			}
		}// fin boton añadir
		
		if(e.getSource()==miVista.getbCancelarCompra()) {
			Planta p ;
			for (int i = 0; i < miVista.getModeloTablaCompras().getRowCount(); i++) {
				p = miVivero.buscarPlanta(miVista.getModeloTablaCompras().
						getValueAt(i, 0).toString());
				miVivero.añadirPlanta(p,Integer.parseInt(miVista.getModeloTablaCompras().
						getValueAt(i, 2).toString()));
			}
			miVista.getModeloTablaCompras().setRowCount(0);
		}//Fin boton finalizar compra
		
		if(e.getSource()==miVista.getbFinalizarCompra()) {
			Object[] options = { "Yes", "No" };
			int aux =JOptionPane.showOptionDialog(miVista, "¿Continuar compra?", "Finalizar compra",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);
			
			if(aux==JOptionPane.YES_OPTION) {
				String nombre = JOptionPane.showInputDialog("Introduce el nombre");
				try (BufferedWriter bout = Files.newBufferedWriter(
								Path.of("./files/" + nombre + Long.toString(System.currentTimeMillis())
								+".txt"),
								Charset.defaultCharset(),
								StandardOpenOption.CREATE)){
					
					bout.append(LocalDate.now().toString()+"\n"+nombre);
					Planta p ;
					for (int i = 0; i < miVista.getModeloTablaCompras().getRowCount(); i++) {
						p = miVivero.buscarPlanta(miVista.getModeloTablaCompras().
								getValueAt(i, 0).toString());
						bout.append("\n"+p.toStringLineal());
						
						}
					miVista.getModeloTablaCompras().setRowCount(0);
					
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		}//Fin boton finalizar compras
		
		if(e.getSource()==miVista.getRbBonsai()) {
			String[] array = new String[5];
			miVista.getModeloTablaCatalogo().setRowCount(0);
			
			for (Bonsai p : miVivero.listaBonsais()) {
				array[0] = p.getIdPlanta();
				array[1] = p.getNombreComun();
				array[2] = Double.toString(p.getStock());
				array[3] = Double.toString(p.getPrecioBase());
				array[4] = Double.toString(p.calculaPrecioVenta());
				miVista.getModeloTablaCatalogo().addRow(array);
			}
			
		}//fin rbBonsai
		
		if(e.getSource()==miVista.getRbPlantaEstandar()) {
			String[] array = new String[5];
			miVista.getModeloTablaCatalogo().setRowCount(0);
			
			for (PlantaEstandar p : miVivero.listaPlantasEstandar()) {
				array[0] = p.getIdPlanta();
				array[1] = p.getNombreComun();
				array[2] = Double.toString(p.getStock());
				array[3] = Double.toString(p.getPrecioBase());
				array[4] = Double.toString(p.calculaPrecioVenta());
				miVista.getModeloTablaCatalogo().addRow(array);
			}		
		}//fin rbPEstandar
		
		if(e.getSource()==miVista.getRbTodas()) {
			String[] array = new String[5];
			miVista.getModeloTablaCatalogo().setRowCount(0);
			
			for (Planta p : miVivero.listaPlantas()) {
				array[0] = p.getIdPlanta();
				array[1] = p.getNombreComun();
				array[2] = Double.toString(p.getStock());
				array[3] = Double.toString(p.getPrecioBase());
				array[4] = Double.toString(p.calculaPrecioVenta());
				miVista.getModeloTablaCatalogo().addRow(array);
			}
		}//fin rbTodas*/
	}

	private String extraeNombre() throws SQLException, MiExcepcion{
		
		
		
		return miDAOC.buscaCodigo(Integer.parseInt(String.valueOf(
				((String) miVistaInicio.getModelocbClientes().getSelectedItem()).charAt(0)))).getNombre();
	}

	
	
	
}
