package org.josfranmc.collocatio.webservice.model;

import org.josfranmc.gutenberg.download.DownloadMode;

/**
 * Encapsula los parámetros que puede recibir el servicio encargado de realizar la descarga de los libros. Son los siguientes:
 * <ul>
 * <li><b>fileType</b>: tipo de ficheros a descargar</li>
 * <li><b>language</b>: idioma de los ficheros a descargar</li> 
 * <li><b>urlBase</b>: url del recurso a descargar</li>
 * <li><b>savePath</b>: ruta local donde descargar los recursos</li>
 * <li><b>overwrite</b>: indica si se deben sobreescribir recursos que ya están descargados</li>
 * <li><b>delay</b>: tiempo de espera entre descargas</li>
 * <li><b>unzip</b>: indica si los recursos descargados en formato zip deben o no descomprimirse</li>
 * <li><b>downloadMode</b>: modo de realizar las descargas</li>
 * </ul>
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see DownloadMode
 */
public class DownloadParameters {
	
	/**
	 * Tipo de ficheros a descargar
	 */
	private String fileType = "txt";
	
	/**
	 * Idioma de los ficheros a descargar
	 */
	private String language = "es";
	
	/**
	 * Ruta de la carpeta en la que obtener los recursos descargados.
	 */
	private String savePath = null;
	
	/**
	 * Si deben sobreescribirse los recursos existentes por las nuevas descargas en caso de ser los mismos
	 */
	private boolean overwrite = false;
	
	/**
	 * Intervalo de espera entre descargas, en milisegundos
	 */
	private int delay = 2000;
	
	/**
	 * Si se deben descomprimir los archivos descargados
	 */
	private boolean unzip = true;
	
	/**
	 * Número maximo de ficheros a descargar. El valor cero indica descargar todos los ficheros disponibles.
	 */
	private int maxFilesToDownload = 0;
	
	/**
	 * Modo de realizar las descargas
	 */
	DownloadMode downloadMode = DownloadMode.SOFT;

	
	/**
	 * Constructor por defecto.
	 */
	public DownloadParameters() {
		super();
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return la ruta de la carpeta en la que obtener los recursos descargados
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * Establece la ruta de la carpeta en la que obtener los recursos descargados.
	 * @param savePath ruta de la carpeta en la que obtener los recursos descargados
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * @return <i>true</i> si se deben sobreescribir los archivos existentes por los descargados en caso de ser los mismos, <i>false</i> en caso contrario
	 */
	public boolean isOverwrite() {
		return overwrite;
	}

	/**
	 * Establece si deben sobreescribirse los recursos existentes por las nuevas descargas en caso de ser los mismos
	 * @param overwrite <i>true</i> si se deben sobreescribir los archivos, <i>false</i> en caso contrario
	 */
	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	/**
	 * @return el intervalo de espera entre descargas, en milisegundos
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Establece el intervalo de espera entre descargas.
	 * @param delay tiempo de espera, en milisegundos
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return <i>true</i> si se deben descomprimir los archivos descargados, <i>false</i> en caso contrario
	 */
	public boolean isUnzip() {
		return unzip;
	}

	/**
	 * Establece si se deben descomprimir los archivos descargados
	 * @param unzip <i>true</i> si se deben descomprimir los archivos descargados, <i>false</i> en caso contrario
	 */
	public void setUnzip(boolean unzip) {
		this.unzip = unzip;
	}

	/**
	 * @return el número máximo de ficheros a descargar
	 */
	public int getMaxFilesToDownload() {
		return maxFilesToDownload;
	}

	/**
	 * Establece el número máximo de ficheros a descargar. El valor cero indica descargar todos los ficheros disponibles.
	 * @param maxFilesToDownload número máximo de ficheros
	 */
	public void setMaxFilesToDownload(int maxFilesToDownload) {
		this.maxFilesToDownload = maxFilesToDownload;
	}

	/**
	 * @return el modo de realizar las descargas
	 * @see DownloadMode
	 */
	public DownloadMode getDownloadMode() {
		return downloadMode;
	}

	/**
	 * Establece el modo de realizar las descargas
	 * @param downloadMode modo de descarga
	 * @see DownloadMode
	 */
	public void setDownloadMode(DownloadMode downloadMode) {
		if (!(downloadMode instanceof DownloadMode)) {
			throw new IllegalArgumentException("Modo de descarga desconocido.");
		}
		this.downloadMode = downloadMode;
	}
}
