package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vista.VistaCliente;
import vista.VistaGestor;
import vista.VistaInicio;

public class Controlador implements ActionListener{
	VistaInicio miVistaInicio;
	VistaCliente miVistaCliente;
	VistaGestor miVistaGestor;

	public Controlador(VistaInicio v) {
		this.miVistaInicio = v;
		
		//cargaComboBoxYTable();
	}
	public Controlador(VistaCliente v) {
		this.miVistaCliente = v;
	}
	
	public Controlador(VistaGestor v) {
		this.miVistaGestor = v;
	}

	/*private void cargaComboBoxYTable() {
		String[] array = new String[5];
		for (Planta p : miVivero.listaPlantas()) {
			miVista.getModeloCbPlantas().addElement(p.getIdPlanta()+" - "+p.getNombreComun());
			array[0] = p.getIdPlanta();
			array[1] = p.getNombreComun();
			array[2] = Double.toString(p.getStock());
			array[3] = Double.toString(p.getPrecioBase());
			array[4] = Double.toString(p.calculaPrecioVenta());
			miVista.getModeloTablaCatalogo().addRow(array);
		}
		
	}
*/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==miVistaInicio.getbIniciar()) {
			miVistaInicio.setVisible(false);
			this.mostrarVistaCliente();
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

	private void mostrarVistaCliente() {
		VistaCliente miVista = new VistaCliente();
		
		Controlador ctr = new Controlador(miVista);
		
		miVista.control(ctr);
		
		JFrame ventana = new JFrame("Gestion de Tienda");
		
		ventana.setContentPane(miVista);
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
		
	}

	
	
	
}
