package test;

import javax.swing.JFrame;

import controlador.Controlador;
import vista.Vista;



public class MainTest {

	public static void main(String[] args) {
		Vista miVista = new Vista();
		
		Controlador ctr = new Controlador(miVista);
		
		miVista.control(ctr);
		
		JFrame ventana = new JFrame("Eventos tipo check, combo y lista");
		
		ventana.setContentPane(miVista);
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

}
