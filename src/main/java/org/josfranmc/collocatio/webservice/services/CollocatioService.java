package org.josfranmc.collocatio.webservice.services;

import java.util.List;

import org.josfranmc.collocatio.algorithms.ParamsAlgorithm;
import org.josfranmc.collocatio.service.QueryType;
import org.josfranmc.collocatio.service.domain.Collocatio;

/**
 * Interfaz que establece los servicios disponibles para tratar con la extracción y consulta de colocaciones.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see ParamsAlgorithm
 * @see QueryType
 * @see Collocatio
 */
public interface CollocatioService {
	
	/**
	 * Ejecuta un algoritmo para realizar la extracción de colocaciones.
	 * @param parameters parámetros de configuración del algoritmo
	 * @see ParamsAlgorithm
	 */
	public void executeAlgorithm(ParamsAlgorithm parameters);

	/**
	 * Obtiene una colocación en base a su identificador en la base de datos.
	 * @param id identificador de la colocación a consultar
	 * @param db nombre de la base de datos en la que consultar
	 * @return un objeto Collocatio que representa la colocación guardada
	 * @see Collocatio
	 */
	public Collocatio findCollocation(long id, String db);	
	
	/**
	 * Ejecuta una consulta sobre la base de datos de colocaciones.
	 * @param queryType tipo de consulta a realizar
	 * @param words lista de palabras a usar como filtro de la consulta
	 * @param offset página a obtener
	 * @param size  cantidad de registros de la página
	 * @param db nombre de la base de datos a la que consultar
	 * @return lista con las colocaciones recuperadas
	 * @see Collocatio
	 */
	public List<Collocatio> findCollocations(String queryType, List<String> words, int offset, int size, String db);	

	/**
	 * Guarda una nueva colocación en la base de datos especificada.
	 * @param collocatio colocación a guardar
	 * @param dbname base de datos donde guardar
	 * @return la colocación guardada
	 * @see Collocatio
	 */
	public Collocatio addCollocation(Collocatio collocatio, String dbname);
	
	/**
	 * Elimina una colocación de la base de datos especificada.
	 * @param id identificador del registro a eliinar
	 * @param dbname base de datos donde eliminar
	 * @return número de registros afectados
	 */
	public long deleteCollocation(Long id, String dbname);
	
	/**
	 * Elimina una base de datos especificada.
	 * @param dbname base de datos a eliminar
	 * @return número de registros afectados
	 */
	public void deleteDataBase(String dbname);
}
