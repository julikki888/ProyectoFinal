package vista;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import controlador.Controlador;


public class VistaGestor extends JFrame{
	
	private static final long serialVersionUID = -968892632994221823L;
	
	public static final Color COLOR_PRINCIPAL = new Color(255, 138, 138),
				COLOR_SECUNDARIO = new Color(115, 0, 0);
	
	
	/**
	 * Variables de instancia
	 */
	private JTextField tfCodigo, tfProveedor, tfNombre, tfStock, tfPrecio;
	private JButton bNuevo, bGuardar, bModificar, bEliminar, bCancelar;
	private JComboBox<String> cbProductos;
	private DefaultComboBoxModel<String> modelocbProductos;
	
	/**
	 * Constructor
	 */
	public VistaGestor() {
		JPanel pPrincipal = new JPanel();
		pPrincipal.setBackground(COLOR_PRINCIPAL);
		
		// Preparar un panel intermedio con todo el contenido
		JPanel p = new JPanel(new BorderLayout(20,20));
		p.setBackground(COLOR_PRINCIPAL);
		p.add(preparaPanelDatos(),       BorderLayout.CENTER);
		p.add(preparaPanelOperaciones(), BorderLayout.EAST);
		p.add(preparaPanelNavegacion(),  BorderLayout.SOUTH);
		
		// Añadir el panel anterior al panel principal
		pPrincipal.add(p);

		//
		this.setContentPane(pPrincipal);
		this.setTitle("Ventana Gestor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);


	}
	
	
	/**
	 * Método que prepara el panel con los datos del disco
	 * @return
	 */
	private JPanel preparaPanelDatos() {
		
		// Pedir memoria para el panel y ponerle un borde
		JPanel panelDatos = new JPanel(new GridLayout(5,2,10,10));
		panelDatos.setBorder(new TitledBorder(new LineBorder(COLOR_SECUNDARIO,3,true),"Gestion de los productos"));
		panelDatos.setBackground(COLOR_PRINCIPAL);
		((TitledBorder)panelDatos.getBorder()).setTitleColor(Color.BLACK);
		((TitledBorder)panelDatos.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));
		
		// Pedir memoria para los JTextField
		tfCodigo = new JTextField();
		tfProveedor  = new JTextField();
		tfNombre = new JTextField(18);  // 18 sera el tamaño de todos los JTextField
		tfStock = new JTextField();
		tfPrecio = new JTextField();
		
		// Añadir los campos de texto y los JLabel que los preceden
		// al panel
		panelDatos.add(new JLabel("Codigo",JLabel.RIGHT));
		panelDatos.add(tfCodigo);
		panelDatos.add(new JLabel("Nombre Producto",JLabel.RIGHT));
		panelDatos.add(tfNombre);
		panelDatos.add(new JLabel("Proveedor",JLabel.RIGHT));
		panelDatos.add(tfProveedor);
		panelDatos.add(new JLabel("Stock",JLabel.RIGHT));
		panelDatos.add(tfStock);
		panelDatos.add(new JLabel("Precio base",JLabel.RIGHT));
		panelDatos.add(tfPrecio);
		
		cargaTFL(panelDatos);//Pasamos el metodo que modifica los TextFields y tambien modifica los JLabel
		
		return panelDatos;
	}
	
	/**
	 * Metodo que pone el color correcto a los TetfField y los hace ineditables
	 */
	public void cargaTFL(Container c) {
	    
	    for (Component componente : c.getComponents()) {
	        
	        if (componente instanceof JTextField) {
	            ((JTextField) componente).setEditable(false);
	            componente.setBackground(Color.white);
	            ((JTextField) componente).setBorder(new LineBorder(COLOR_SECUNDARIO,2));
	        }else if (componente instanceof JLabel) {
	            ((JLabel) componente).setForeground(Color.BLACK);
	        }
	    }
	}
	
	
	/**
	 * Método que prepara un panel con todos los botones de 
	 * operaciones permitidas sobre la BD
	 */
	private JPanel preparaPanelOperaciones() {
		
		JPanel p = new JPanel(new GridLayout(5,1,10,10));
		p.setBackground(COLOR_PRINCIPAL);
		
		// Pedir memoria para los botones
		bNuevo = new JButton("Nuevo");
		bGuardar = new JButton("Regresar");
		bModificar = new JButton("         Modificar         ");
		bEliminar = new JButton("Eliminar");
		bCancelar = new JButton("Cancelar");
		
		// Añadir los botones al panel
		p.add(bNuevo);
		p.add(bGuardar);
		p.add(bModificar);
		p.add(bEliminar);
		p.add(bCancelar);
		
		// Poner el borde y el color a todos los botones
		cargaButton(p);
		
		return p;
	}
	
	/**
	 * Metodo que recore el panel para que cambie el color a todos los botones ponga el borde raised
	 */
	public void cargaButton(Container c) {
	    
	    for (Component componente : c.getComponents()) {
	        
	        if (componente instanceof JButton) {
	            componente.setBackground(COLOR_SECUNDARIO);
	            ((JButton) componente).setBorder(new BevelBorder(BevelBorder.RAISED));
	            ((JButton) componente).setForeground(Color.WHITE);
	        }
	    }
	}
	
	
	/**
	 * Prepara el panel que contendra los botones de "navegacion" por
	 * los distintos registros (tuplas) de la tabla
	 */
	private JPanel preparaPanelNavegacion() {
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
		p.setBackground(COLOR_PRINCIPAL);
		p.setBorder(new TitledBorder(new LineBorder(COLOR_SECUNDARIO,3,true),"Productos"));
		((TitledBorder)p.getBorder()).setTitleColor(Color.BLACK);
		((TitledBorder)p.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));
		
		modelocbProductos = new DefaultComboBoxModel<String>();
		cbProductos = new JComboBox<>(modelocbProductos);  
		cbProductos.setBorder(new LineBorder(COLOR_SECUNDARIO));
		
		p.add(cbProductos);
		
		return p;
	}
	
	
	/**
	 * Método que da a los botones control de accion
	 */
	public void control(Controlador ctr) {

		// Control de los botones de operaciones
		bNuevo.addActionListener(ctr);
		bGuardar.addActionListener(ctr);
		bModificar.addActionListener(ctr);
		bCancelar.addActionListener(ctr);
		bEliminar.addActionListener(ctr);
	}


	/** 
	 * M�todos get de todas la variables de instancia
	 */
	public JTextField getTfCodigo() {return tfCodigo;}
	public JTextField getTfAutor()  {return tfProveedor;}
	public JTextField getTfTitulo() {return tfNombre;}
	public JTextField gettfFechaPubli() {return tfStock;}
	public JTextField getTfPrecio() {return tfPrecio;}

	public JButton getbNuevo()     {return bNuevo;}
	public JButton getbGuardar()   {return bGuardar;}
	public JButton getbModificar() {return bModificar;}
	public JButton getbEliminar()  {return bEliminar;}
	public JButton getbCancelar()  {return bCancelar;	}
	
	public JComboBox<String> getCbProductos() {return cbProductos;}
	public DefaultComboBoxModel<String> getModelocbProductos() {return modelocbProductos;}

}













