package vista;

import java.awt.*;

import javax.swing.*;

import controlador.Controlador;

public class VistaInicio extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Variable de instancia
	 */
	private JComboBox<String> cbClientes;
	private DefaultComboBoxModel<String> modelocbClientes;

	private JButton bIniciar, bGestion;
	
	
	public VistaInicio() {
		JPanel p = new JPanel(new BorderLayout());
	
		
		p.add(panelNorte(),BorderLayout.NORTH);
		p.add(panelCentral(),BorderLayout.CENTER);
		p.add(panelSur(),BorderLayout.SOUTH);
		
		this.add(p);
	}
	
	private JPanel panelNorte() {
		JPanel p = new JPanel();
		
		JLabel l = new JLabel("Inicia Sesión");
		l.setFont(new Font("Arial", Font.BOLD, 24));
		
		p.add(l);
		
		return p;
	}

	private JPanel panelCentral() {
		JPanel p = new JPanel();		
		
		modelocbClientes = new DefaultComboBoxModel<>();
		cbClientes = new JComboBox<>(modelocbClientes);
		bIniciar = new JButton("Iniciar");
		
		p.add(cbClientes);
		p.add(bIniciar);
		
		return p;
	}
	
	
	private JPanel panelSur() {	
		JPanel p = new JPanel();
		
		bGestion = new JButton("Inicio sesion Gestor");
		p.add(bGestion);
		
		return p;
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
