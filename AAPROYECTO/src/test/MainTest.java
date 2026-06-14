package test;

import javax.swing.JFrame;

import controlador.Controlador;
import vista.*;



public class MainTest {

	public static void main(String[] args) {
		VistaInicio miVista = new VistaInicio();
		VistaCliente miVistaCliente = new VistaCliente();
		VistaGestor miVistaGestor = new VistaGestor();
		
		Controlador ctr = new Controlador(miVista,miVistaCliente,miVistaGestor);
		
		miVista.control(ctr);		
		miVistaCliente.control(ctr);
		miVistaGestor.control(ctr);
		
	}

}
