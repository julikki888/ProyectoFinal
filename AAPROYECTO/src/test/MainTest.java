package test;

import javax.swing.JFrame;

import controlador.Controlador;
import vista.VistaInicio;



public class MainTest {

	public static void main(String[] args) {
		VistaInicio miVista = new VistaInicio();
		
		Controlador ctr = new Controlador(miVista);
		
		miVista.control(ctr);
		
		JFrame ventana = new JFrame("INICIO DE SESION");
		
		ventana.setContentPane(miVista);
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

}
