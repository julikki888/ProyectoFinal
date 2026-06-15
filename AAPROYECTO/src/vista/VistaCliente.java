package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import controlador.Controlador;

public class VistaCliente extends JFrame{

	private static final long serialVersionUID = -5752211613049689258L;

	public static final Color COLOR_PRINCIPAL = new Color(255, 176, 176),
			COLOR_SECUNDARIO = new Color(115, 0, 0);

	
	/**
	 * Variables de instancia
	 */
	
	private JComboBox<String> cbProductos;
	private DefaultComboBoxModel<String> modeloCbProductos;

	private JButton bInfo, bAñadirCarrito;
	private JComboBox<Integer> cbUnidades;
	
	private JTable tablaCompras;
	private DefaultTableModel modeloTablaCompras;
	
	private JButton bCancelarCompra, bFinalizarCompra;
	
	private JRadioButton rbTodas, rbCerdo, rbTernera, rbPollo;

	private JTable tablaCatalogo;
	private DefaultTableModel modeloTablaCatalogo;
	
	private JLabel lCliente;
	private JButton bHistorialCliente, bFavoritos, bRegresar;

	/**
	 * Constructor
	 */
	public VistaCliente() {
		JPanel p = new JPanel();
		p.setBackground(COLOR_PRINCIPAL);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.setBackground(Color.white);
		panelPrincipal.setBorder(new EmptyBorder(15,15,15,15));

		panelPrincipal.add(preparaPanelTitulo());
		panelPrincipal.add(preparaPanelCompras());
		panelPrincipal.add(preparaPanelCatalogo());
		panelPrincipal.add(preparaPanelCliente());
		
		p.add(panelPrincipal);
		
		cargaContenedor(p);

		this.setContentPane(p);
		this.setTitle("Ventana Cliente");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);
		setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage("img/logo.png"));
	}



	/**
	 * Método que se encarga de prepara el panel con el título y el logo
	 * @return
	 */
	private JPanel preparaPanelTitulo() {
		JPanel pTitulo = new JPanel();
		pTitulo.setBackground(Color.white);

		JLabel titulo = new JLabel("-- Carnicerias Picasso --", JLabel.CENTER);
		titulo.setForeground(Color.decode("#5E0606"));
		titulo.setFont(new Font("Comic Sans MS",Font.BOLD, 28));
		
		pTitulo.add(new JLabel(new ImageIcon("./img/logo.png")));
		pTitulo.add(titulo);
		
		return pTitulo;
	}
	
	/**
	 * Método que se encarga de prepara el panel de Compras
	 * @return
	 */
	private JPanel preparaPanelCompras() {
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(new LineBorder(COLOR_SECUNDARIO,3,true),"Área de compras de clientes"));
		((TitledBorder)((JPanel)p).getBorder()).setTitleColor(Color.BLACK);
		((TitledBorder)((JPanel)p).getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));		
	
		p.add(preparaPNorte(), BorderLayout.NORTH);
		p.add(preparaPCentro(), BorderLayout.CENTER);

		// ---------------------------------------------
		// Prepara la parte sur de este panel de Compras
		// ---------------------------------------------
		JPanel pSur = new JPanel();
		pSur.setBackground(Color.white);
		bCancelarCompra = new JButton("Cancelar Compra");
		bFinalizarCompra = new JButton("Finalizar Compra");
		
		bCancelarCompra.setPreferredSize(new Dimension(150,25));
		bFinalizarCompra.setPreferredSize(new Dimension(150,25));
		
		pSur.add(bCancelarCompra);
		pSur.add(bFinalizarCompra);
		
		p.add(pSur, BorderLayout.SOUTH);
		
		cargaButton(pSur);

		return p;
	}
	
	
	/**
	 * Preparación del panel de la parte superior del panel de Compras
	 * @return
	 */
	private JPanel preparaPNorte() {

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		pNorte.setBackground(Color.white);

		// El comboBox de productos tiene un DefaultComboBoxModel en su interior, ya que este objeto nos
		// va a permitir el uso de muchos más métodos para modificar el combo durante la ejecución
		modeloCbProductos = new DefaultComboBoxModel<>();
		cbProductos = new JComboBox<String>(modeloCbProductos);
		cbProductos.setPreferredSize(new Dimension(200,25));
		cbProductos.setBorder(new LineBorder(COLOR_SECUNDARIO));

		bInfo = new JButton("Información");
		
		cbUnidades = new JComboBox<Integer>();
		cbUnidades.setBorder(new LineBorder(COLOR_SECUNDARIO));
		for (int i=1; i<=20; i++)
			cbUnidades.addItem(i);
		
		bAñadirCarrito = new JButton("Añadir al carrito");
		
		// Añadir todos los componentes al panel del norte
		pNorte.add(new JLabel("Producto"));
		pNorte.add(cbProductos);
		pNorte.add(bInfo);
		pNorte.add(new JLabel("Unidades"));
		pNorte.add(cbUnidades);
		pNorte.add(bAñadirCarrito);
		
		cargaButton(pNorte);
		cargaL(pNorte);
		
		return pNorte;
	}
	
	/**
	 * Preparación del panel de la parte central del panel de compras - JTable
	 * @return
	 */
	private JPanel preparaPCentro() {

		JPanel p = new JPanel();
		
		// Crear un objeto DefaultTableModel que estará dentro del JTable
		modeloTablaCompras = new DefaultTableModel();
		tablaCompras = new JTable(modeloTablaCompras);
		
		// Alinear los encabezados de las columnas a la izquierda
		JTableHeader header = tablaCompras.getTableHeader();
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// Definir las columnas y sus títulos
		String [] nombreColumnas = {"Ident.","Nombre","Uds.","Precio Ud.","Precio Total"};
		modeloTablaCompras.setColumnIdentifiers(nombreColumnas);
		
		// Dar tamaños a las columnas
		TableColumnModel columnModel = tablaCompras.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		
		// Colocar el JTable en un scrollpane
		JScrollPane sp = new JScrollPane(tablaCompras);
		sp.setPreferredSize(new Dimension(550,100));
		p.add(sp);
		
		return p;		
	}
	
	
	/**
	 * Método que prepara el panel con el catálogo de carne a mostrar
	 * @return
	 */
	private JPanel preparaPanelCatalogo() {
	
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(new LineBorder(COLOR_SECUNDARIO,3,true),"Catálogo de la tienda"));
		((TitledBorder)((JPanel)p).getBorder()).setTitleColor(Color.BLACK);
		((TitledBorder)((JPanel)p).getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));	
		
		p.add(preparaPNorteCatalogo(), BorderLayout.NORTH);
		p.add(preparaPCentroCatalogo(), BorderLayout.SOUTH);
		
		return p;
	}


	/**
	 * Prepara el panel norte de la parte del catalogo con los JRadioButtons
	 * @return
	 */
	private JPanel preparaPNorteCatalogo() {
		
		// Prepara el grupo de radio buttons
		ButtonGroup bgTipo = new ButtonGroup();
		rbTodas = new JRadioButton("Todas",true);
		rbCerdo = new JRadioButton("Carne de Cerdo",false);
		rbTernera = new JRadioButton("Carne de Ternera");
		rbPollo = new JRadioButton("Carne de Pollo");
		
		bgTipo.add(rbTodas);
		bgTipo.add(rbCerdo);
		bgTipo.add(rbTernera);
		bgTipo.add(rbPollo);
		
		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pNorte.setBackground(Color.white);
		
		pNorte.add(rbTodas);
		pNorte.add(rbCerdo);
		pNorte.add(rbTernera);
		pNorte.add(rbPollo);
		
		cargaRButton(pNorte);
		
		return pNorte;
	}
	
	/**
	 * Método que prepara el JTable del panel del catálogo
	 * @return
	 */
	private JScrollPane preparaPCentroCatalogo() {

		// Crear un objeto DefaultTableModel que estará dentro del JTable
		modeloTablaCatalogo = new DefaultTableModel();
		tablaCatalogo = new JTable(modeloTablaCatalogo);
		
		// Alinear los encabezados de las columnas a la izquierda
		JTableHeader header = tablaCatalogo.getTableHeader();
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		// Definir las columnas y sus títulos
		String [] nombreColumnas = {"Ident.","Nombre","Stock","Precio base","Precio venta"};
		modeloTablaCatalogo.setColumnIdentifiers(nombreColumnas);
		
		// Dar tamaños a las columnas
		TableColumnModel columnModel = tablaCatalogo.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		
		// Colocar el JTable en un scrollpane
		JScrollPane sp = new JScrollPane(tablaCatalogo);
		sp.setPreferredSize(new Dimension(550,100));
		
		return sp;
	}


	
	/**
	 * Metodo que prepara el Panel de los clientes,
	 * en el se mostrara el nombre del cliente que haya iniciado sesion, y tres botones,
	 * uno sera el historial de compras, otro productos favoritos del cliente, y
	 * el ultimo sera un boton para salir
	 */
	private JPanel preparaPanelCliente() {
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(new LineBorder(COLOR_SECUNDARIO,3,true),"Informacion Cliente"));
		((TitledBorder)((JPanel)p).getBorder()).setTitleColor(Color.BLACK);
		((TitledBorder)((JPanel)p).getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));	
		
		p.add(panelCentralClientes(),BorderLayout.CENTER);
		p.add(panelSurClientes(),BorderLayout.SOUTH);
		
		return p;
	}
	
	/**
	 * Metodo para preparar el panel central de clientes
	 */
	private JPanel panelCentralClientes() {
		JPanel p = new JPanel();
		
		JLabel l = new JLabel("Bienvenido: ");
		
		lCliente = new JLabel("Nombre Cliente");
		lCliente.setFont(new Font("Arial", Font.BOLD, 24));
		
		p.add(l);
		p.add(lCliente);
		cargaL(p);

		return p;
	}
	
	/**
	 * Metodo para preparar el panel sur de clientes
	 */
	private JPanel panelSurClientes() {
		JPanel p = new JPanel();
		
		bHistorialCliente = new JButton("Historial de Compras");
		bFavoritos = new JButton("Mis Productos Favoritos");
		bRegresar = new JButton("Volver al Inicio");
		
		p.add(bHistorialCliente);
		p.add(bFavoritos);
		p.add(bRegresar);
		cargaButton(p);
		
		return p;
	}
	
	
	/**
	 * Metodo que recore los Contenedores poniendo los JPanels del color principal
	 */
	public void cargaContenedor(Container c) {
	    
	    for (Component componente : c.getComponents()) {
	        
	    	if (componente instanceof JPanel) {
	    		((JPanel) componente).setBackground(COLOR_PRINCIPAL);
	    		cargaContenedor((Container) componente);
	        }
	    }
	}
	
	
	/**
	 * Metodo que recore el panel para que cambie el color a todos los botones ponga el borde raised
	 */
	public void cargaButton(Container c) {
	    
	    for (Component componente : c.getComponents()) {
	        
	        if (componente instanceof JButton) {
	            componente.setBackground(COLOR_SECUNDARIO);
	            ((JButton) componente).setForeground(Color.WHITE);
	            
	            // 1. Creamos el efecto 3D (puede ser BevelBorder.RAISED o LOWERED)
	            BevelBorder borde3D = new BevelBorder(BevelBorder.RAISED);

	            // 2. Creamos el margen interno transparente para darle el tamaño extra
	            EmptyBorder margenInterno = new EmptyBorder(2, 13, 2, 13); // (arriba, izquierda, abajo, derecha)

	            // 3. Los fusionamos: el 3D se queda EXTERIOR y el margen se queda INTERIOR
	            ((JButton) componente).setBorder(new CompoundBorder(borde3D, margenInterno));
	        }
	    }
	}
	
	/**
	 * Metodo que recore el panel para que cambie el color a todos los botones ponga el borde raised
	 */
	public void cargaRButton(Container c) {
	    
	    for (Component componente : c.getComponents()) {
	        
	        if (componente instanceof JRadioButton) {
	            componente.setBackground(COLOR_PRINCIPAL);
	            ((JRadioButton) componente).setForeground(Color.BLACK);
	            ((JRadioButton) componente).setBorder(new LineBorder(COLOR_SECUNDARIO,2));
	        }
	    }
	}
	
	/**
	 * Metodo que pone los textos en negro
	 */
	public void cargaL(Container c) {
	    
	
	    for (Component componente : c.getComponents()) {
	        
	    	if (componente instanceof JLabel) {
	            ((JLabel) componente).setForeground(Color.BLACK);
	        }
	    }
	}
	
	/**
	 * Métodos getter
	 */
	public JComboBox<String> getCbProductos() {return cbProductos;}
	public DefaultComboBoxModel<String> getModeloCbProductos() {return modeloCbProductos;}

	public JButton getbInfo() {return bInfo;}
	public JButton getbAñadirCarrito() {return bAñadirCarrito;}

	public JComboBox<Integer> getCbUnidades() {return cbUnidades;}

	public JTable getTablaCompras() {return tablaCompras;}
	public DefaultTableModel getModeloTablaCompras() {return modeloTablaCompras;	}

	public JButton getbCancelarCompra() {return bCancelarCompra;}
	public JButton getbFinalizarCompra() {return bFinalizarCompra;}

	public JRadioButton getRbTodas() {return rbTodas;}
	public JRadioButton getRbCerdo() {return rbCerdo;}
	public JRadioButton getRbTernera() {return rbTernera;}
	public JRadioButton getRbPollo() {return rbPollo;}

	public JTable getTablaCatalogo() {return tablaCatalogo;}
	public DefaultTableModel getModeloTablaCatalogo() {	return modeloTablaCatalogo;}
		
	public static long getSerialversionuid() {return serialVersionUID;}
	public JLabel getlCliente() {return lCliente;}
	public JButton getbHistorialCliente() {return bHistorialCliente;}
	public JButton getbFavoritos() {return bFavoritos;}
	public JButton getbRegresar() {return bRegresar;}



	/**
	 * Método que añade el control de eventos a los diferentes componentes
	 * @param ctr
	 */
	public void control(Controlador ctr) {
		
		this.bInfo.addActionListener(ctr);
		this.bAñadirCarrito.addActionListener(ctr);
		this.bCancelarCompra.addActionListener(ctr);
		this.bFinalizarCompra.addActionListener(ctr);
		
		this.rbTernera.addActionListener(ctr);
		this.rbCerdo.addActionListener(ctr);
		this.rbTodas.addActionListener(ctr);
		this.rbPollo.addActionListener(ctr);
		
		this.bRegresar.addActionListener(ctr);
	}
}
