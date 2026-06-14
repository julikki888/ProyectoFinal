package modelo;

import java.util.TreeMap;

	public class GestionTienda {
		/**
		 * Variable de Instancia
		 */
		private TreeMap<String, Carne> mapaProductos;
		private DAOProductos dao;
		
		public GestionTienda(DAOProductos dao) {
			this.dao = dao;
		}
	}
