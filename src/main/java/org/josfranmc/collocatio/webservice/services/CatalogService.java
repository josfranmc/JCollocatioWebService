package org.josfranmc.collocatio.webservice.services;

import java.util.List;

import org.josfranmc.collocatio.webservice.model.CatalogParameters;
import org.josfranmc.gutenberg.catalog.Book;

/**
 * Interfaz que establece los servicios disponibles para la creación y consulta del catálogo de libros.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 */
public interface CatalogService {

	/**
	 * Crea el catálogo de libros.
	 * @param parameters parámetros de configuración
	 * @see CatalogParameters
	 */
	public void createCatalog(CatalogParameters parameters);
	
	/**
	 * Devuelve la información de un libro concreto.
	 * @param id identificador del libro a consultar
	 * @return objeto Book con la información del libro consultado.
	 */
	public Book getBook(String id);
	
	/**
	 * Devuelve una lista de libros según los identificadores pasados.
	 * @param ids lista deidentificadores de los libros a recuperar
	 * @return lista de objetos de tipo Book
	 * @see Book
	 */
	public List<Book> getBooksById(List<String> ids);
	
	/**
	 * @return la ruta donde se guardan los ficheros de la base de datos
	 */
	public String getPathDb();
	
}
