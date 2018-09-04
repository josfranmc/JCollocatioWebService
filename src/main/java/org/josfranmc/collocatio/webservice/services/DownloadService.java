package org.josfranmc.collocatio.webservice.services;

import org.josfranmc.collocatio.webservice.model.DownloadParameters;

/**
 * Interfaz que establece los servicios disponibles para tratar con la descarga de libros.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see DownloadParameters
 */
public interface DownloadService {

	/**
	 * Realiza la descarga de libros. Recibe una serie de parámetros de configuración.
	 * @param parameters parámetros de configuración
	 * @see DownloadParameters
	 */
	public void downloadBooks(DownloadParameters parameters);
}
