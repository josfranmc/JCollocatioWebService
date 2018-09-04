package org.josfranmc.collocatio.webservice.model;

/**
 * Encapsula los parámetros que puede recibir el servicio encargado de crear la base de datos del catálogo de libros:
 * <ul>
 * <li><b>pathRDFs</b>: ruta de los ficheros RDF a procesar</li>
 * </ul>
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 */
public class CatalogParameters {

	private String pathRDFs;
	
	
	/**
	 * Constructor por defecto.
	 */
	public CatalogParameters() {
		
	}

	/**
	 * @return la ruta de los ficheros RDF a procesar
	 */
	public String getPathRDFs() {
		return pathRDFs;
	}

	/**
	 * Establece la ruta de los ficheros RDF a procesar.
	 * @param pathRDFs ruta de los ficheros
	 */
	public void setPathRDFs(String pathRDFs) {
		this.pathRDFs = pathRDFs;
	}
}
