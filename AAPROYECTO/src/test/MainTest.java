package test;

import java.sql.SQLException;


import controlador.Controlador;
import modelo.MiExcepcion;
import vista.*;



public class MainTest {

	public static void main(String[] args) {
		VistaInicio miVista = new VistaInicio();
		VistaCliente miVistaCliente = new VistaCliente();
		VistaGestor miVistaGestor = new VistaGestor();
		
		Controlador ctr;
		

		try {
			ctr = new Controlador(miVista,miVistaCliente,miVistaGestor);
			
			miVista.control(ctr);		
			miVistaCliente.control(ctr);
			miVistaGestor.control(ctr);
			
		} catch (SQLException | MiExcepcion e) {
			e.printStackTrace();
		}
		
	
		
	}

}
