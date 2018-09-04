package org.josfranmc.collocatio.webservice.services;

import java.util.List;

import org.josfranmc.collocatio.webservice.model.CatalogParameters;
import org.josfranmc.gutenberg.catalog.Book;
import org.josfranmc.gutenberg.catalog.JGutenbergCatalog;
import org.springframework.stereotype.Service;

/**
 * Implementa los servicios que permiten manejar todo lo relacionado con el catálogo de libros: creación y consulta de datos.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see CatalogService
 * @see CatalogParameters
 */
@Service
public class CatalogServiceImpl implements CatalogService {

	private JGutenbergCatalog catalog;
	
	
	/**
	 * Constructor por defecto. Crea una instancia del catálogo de libros y arranca la base de datos.
	 */
	public CatalogServiceImpl() {
		catalog = JGutenbergCatalog.getInstance(true);
	}
	
	/**
	 * Carga los datos de los libros en la base de datos.<br>
	 * En los parámetros de conficuración se debe indicar la ruta que contiene los ficheros RDF a procesar, que son los que contienen los datos de los libros.
	 * @param parameters parámetros de configuración
	 * @see CatalogParameters
	 */
	@Override
	public void createCatalog(CatalogParameters parameters) {
		catalog.createCatalog(parameters.getPathRDFs());
	}

	
	@Override
	public Book getBook(String id) {
		return catalog.getBookById(id);
	}

	@Override
	public List<Book> getBooksById(List<String> ids) {
		return null;
	}
	
	/**
	 * @return la ruta donde se guardan los ficheros de la base de datos
	 */
	public String getPathDb() {
		return catalog.getPathDb();
	}
}
