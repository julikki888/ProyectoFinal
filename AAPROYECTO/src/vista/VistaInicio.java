package vista;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Controlador;

public class VistaInicio extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Color COLOR_PRINCIPAL = new Color(255, 176, 176),
			COLOR_SECUNDARIO = new Color(115, 0, 0);
	
	/**
	 * Variable de instancia
	 */
	private JComboBox<String> cbClientes;
	private DefaultComboBoxModel<String> modelocbClientes;

	private JButton bIniciar, bGestion;
	
	
	public VistaInicio() {
		JPanel pPrincipal = new JPanel();		
		
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(450,150));
		
		pPrincipal.setBackground(COLOR_PRINCIPAL);
		p.setBorder(new LineBorder(COLOR_SECUNDARIO,3,true));
		
		p.add(panelNorte(),BorderLayout.NORTH);
		p.add(panelCentral(),BorderLayout.CENTER);
		p.add(panelSur(),BorderLayout.SOUTH);
		
		pPrincipal.add(p);
		cargaContenedor(pPrincipal);
		
		this.setTitle("Inicio de Sesión");
		this.setContentPane(pPrincipal);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage("img/logo.png"));

	}
	
	private JPanel panelNorte() {
		JPanel p = new JPanel();
		
		JLabel l = new JLabel("Inicia Sesión");
		l.setFont(new Font("Arial", Font.BOLD, 24));
		l.setForeground(Color.BLACK);
		
		p.add(l);
		
		return p;
	}

	private JPanel panelCentral() {
		JPanel p = new JPanel();		
		
		modelocbClientes = new DefaultComboBoxModel<>();
		cbClientes = new JComboBox<>(modelocbClientes);
		bIniciar = new JButton("Iniciar");
		
		cbClientes.setBorder(new LineBorder(COLOR_SECUNDARIO));
		
		p.add(cbClientes);
		p.add(bIniciar);
		cargaButton(p);
		
		return p;
	}
	
	
	private JPanel panelSur() {	
		JPanel p = new JPanel();
		
		bGestion = new JButton("Inicio sesion Gestor");
		p.add(bGestion);
		
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
	            ((JButton) componente).setForeground(Color.WHITE);
	            // 1. Creamos el efecto 3D (puede ser BevelBorder.RAISED o LOWERED)
	            BevelBorder borde3D = new BevelBorder(BevelBorder.RAISED);

	            // 2. Creamos el margen interno transparente para darle el tamaño extra
	            EmptyBorder margenInterno = new EmptyBorder(3, 16, 3, 16); 

	            // 3. Los fusionamos: el 3D se queda EXTERIOR y el margen se queda INTERIOR
	            ((JButton) componente).setBorder(new CompoundBorder(borde3D, margenInterno));
	        }
	    }
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
	
	
	public void control(Controlador ctr) {
		bGestion.addActionListener(ctr);
		bIniciar.addActionListener(ctr);
	}
		
	/**
	 * Getters and setters
	 * @return
	 */
	public JComboBox<String> getCbClientes() {return cbClientes;}
	public DefaultComboBoxModel<String> getModelocbClientes() {return modelocbClientes;}
	public JButton getbIniciar() {return bIniciar;}
	public JButton getbGestion() {return bGestion;}
	
	public void setCbClientes(JComboBox<String> cbClientes) {this.cbClientes = cbClientes;}
	public void setModelocbClientes(DefaultComboBoxModel<String> modelocbClientes) {this.modelocbClientes = modelocbClientes;}
	public void setbIniciar(JButton bIniciar) {this.bIniciar = bIniciar;}
	public void setbGestion(JButton bGestion) {this.bGestion = bGestion;}

	
	
	
}
