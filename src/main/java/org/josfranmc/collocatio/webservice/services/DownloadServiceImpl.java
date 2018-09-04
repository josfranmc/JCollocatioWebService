package org.josfranmc.collocatio.webservice.services;

import org.josfranmc.collocatio.webservice.model.DownloadParameters;
import org.josfranmc.gutenberg.download.IGutenbergDownload;
import org.josfranmc.gutenberg.download.JGutenbergDownloadFactory;
import org.springframework.stereotype.Service;

/**
 * Implementa los servicios encargado de realizar la descarga de libros.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see DownloadService
 * @see DownloadParameters
 * @see IGutenbergDownload
 * @see JGutenbergDownloadFactory
 */
@Service
public class DownloadServiceImpl implements DownloadService {

	/**
	 * Realiza la descarga de libros utilizando un objeto de tipo IGutenbergDownload, el cual es configurado con los parámetros que se 
	 * reciben.
	 * @param parameters parámetros de configuración
	 * @see DownloadParameters
	 * @see IGutenbergDownload
	 * @see JGutenbergDownloadFactory
	 */
	@Override
	public void downloadBooks(DownloadParameters parameters) {
		IGutenbergDownload jg = JGutenbergDownloadFactory.create();

		jg.setFileType(parameters.getFileType());
		jg.setLanguage(parameters.getLanguage());
		jg.setSavePath(parameters.getSavePath());
		jg.setOverwrite(parameters.isOverwrite());
		jg.setDelay(parameters.getDelay());
		jg.setUnzip(parameters.isUnzip());
		jg.setMaxFilesToDownload(parameters.getMaxFilesToDownload());
		jg.setDownloadMode(parameters.getDownloadMode());

		jg.downloadBooks();
	}
}
