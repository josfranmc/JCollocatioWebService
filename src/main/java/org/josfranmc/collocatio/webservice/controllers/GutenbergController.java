package org.josfranmc.collocatio.webservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;

import org.josfranmc.collocatio.webservice.model.CatalogParameters;
import org.josfranmc.collocatio.webservice.model.Response;
import org.josfranmc.collocatio.webservice.model.DownloadParameters;
import org.josfranmc.collocatio.webservice.services.CatalogService;
import org.josfranmc.collocatio.webservice.services.DownloadService;
import org.josfranmc.gutenberg.catalog.Book;

/**
 * Controlador que gestiona todoas las peticiones relacionadas con la descarga de libros almacenados en los repositorios del proyecto Gutenberg.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see DownloadParameters
 */
@RestController
@RequestMapping("/gutenberg")
public class GutenbergController {

	@Autowired
	private DownloadService downloadService;
	
	@Autowired
	private CatalogService catalogSevice;
	
	/**
	 * Respuesta a devolver a las peticiones realizadas
	 */
	@Autowired
	private Response response;
	
	
	/**
	 * @return un JSON con los valores por defecto de los parámetros que se pueden utilizar a la hora de realizar la descarga de libros
	 */
	@RequestMapping(value = "/download/info_parameters", method = RequestMethod.GET)
	public ResponseEntity<DownloadParameters> getDefaultParameters() {
		return new ResponseEntity<DownloadParameters>(new DownloadParameters(), HttpStatus.OK);
	}	
	
	/**
	 * Realiza la descarga de libros.<p>
	 * Recibe un JSON con los parámetros de configuración, el cual debe tener la siguiente estructura 
	 * (los valores de cada parámetro son a modo de ejemplo):<p>
	 * {<br>
     * "fileType": "txt",<br>
     * "language": "es",<br>
     * "savePath": "C:\\ruta\descarga\\",<br>
     * "overwrite": false,<br>
     * "delay": 2000,<br>
     * "unzip": true,<br>
     * "maxFilesToDownload": 2,<br>
     * "downloadMode": "SOFT"<br>
	 *}<p>El JSON recibido se mapea a un objeto de tipo DownloadParameters
	 * @param parameters parámetros de configuración
	 * @return un JSON con el resultado del proceso
	 * @see DownloadParameters
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<Response> downloadBooks(@RequestBody DownloadParameters parameters) {
		downloadService.downloadBooks(parameters);
		
		response.setPath("/gutenberg/download");
		response.setStatus(HttpStatus.OK.toString());
		response.setMessage("Descarga realizada");
		response.setError(null);
		response.setTimestamp(getCurrentTime());
		
		return  new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Carga en la bas de datos el catálogo de libros en base a los ficheros RDFs indicados.<br>
	 * Recibe un JSON con los parámetros de configuración, el cual debe tener la siguiente estructura 
	 * (los valores de cada parámetro son a modo de ejemplo):<br>
	 * {<br>
     * "pathRDFs": "<i>ruta_de_los_ficheros_RDF</i>"<br>
	 * }
	 *<p>El JSON recibido se mapea a un objeto de tipo CatalogParameters 
	 * @param parameters parámetros de configuración
	 * @return un JSON con el resultado del proceso
	 * @see CatalogParameters
	 */
	@RequestMapping(value = "/catalog/create", method = RequestMethod.POST)
	public ResponseEntity<Response> createCatalog(@RequestBody CatalogParameters parameters) {

		catalogSevice.createCatalog(parameters);
		
		response.setPath("/gutenberg/catalog/create");
		response.setStatus(HttpStatus.OK.toString());
		response.setMessage("Cargados datos en base de datos: " + catalogSevice.getPathDb());
		response.setError(null);
		response.setTimestamp(getCurrentTime());
		
		return  new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Devuelve los datos de un libro concreto.
	 * @param id identificador del libro a consultar
	 * @return un objeto Book con los datos del libro consultado
	 */
	@RequestMapping(value = "/catalog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBook(@PathVariable("id") String id) {
		Book book = catalogSevice.getBook(id);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}
	/*
	@ExceptionHandler(OfertaInexistenteException.class)
	public ResponseEntity<String> gestionarNoExistentes(OfertaInexistenteException oie) {
	    return new ResponseEntity<String>(oie.getMessage(), HttpStatus.NOT_FOUND);
	}*/
	
	private Instant getCurrentTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.toInstant();
	}
}
