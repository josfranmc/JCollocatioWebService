package org.josfranmc.collocatio.webservice.services;

import java.util.ArrayList;
import java.util.List;

import org.josfranmc.collocatio.JCollocatio;
import org.josfranmc.collocatio.algorithms.ParamsAlgorithm;
import org.josfranmc.collocatio.db.ConnectionFactory;
import org.josfranmc.collocatio.service.domain.Collocatio;
import org.josfranmc.collocatio.service.ICollocatioService;
import org.josfranmc.collocatio.service.JCollocatioService;
import org.josfranmc.collocatio.service.QueryType;
import org.springframework.stereotype.Service;

/**
 * Implementa los servicios que permiten manejar todo lo relacionado con las colocaciones, como ejecutar algoritmos para realizar su extracción
 * o consultar en base a diversos parámetros las que están almacenadas.   
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see ICollocatioService
 * @see JCollocatioService
 * @see CollocatioService
 * @see ParamsAlgorithm
 * @see QueryType
 */
@Service
public class CollocatioServiceImpl implements CollocatioService {

	/**
	 * Permite realizar extracciones de colocaciones
	 */
	private JCollocatio collocatio;;
	
	/**
	 * Permite consultar las coloaciones almacenadas
	 */
	private ICollocatioService collocatioService;

	
	/**
	 * Constructor por defecto. Crea un objeto JCollocatio, que permite realzia rla extracción de coloaciones, y un objeto JCollocatioService,
	 * que permite realizar consultas de las colocaciones almacenadas.
	 */
	public CollocatioServiceImpl() {
		this.collocatio = new JCollocatio();
		this.collocatioService = new JCollocatioService();
	}

	/**
	 * Realiza la extracción de colocaciones. Recibe un JSON con los parámetros de configuración, el cual debe tener la siguiente estructura
	 * (los valores de cada parámetro son a modo de ejemplo):<br>
	 * {<br>
     * &nbsp;&nbsp;&nbsp;"algorithmType": "MUTUAL_INFORMATION",<br>
     * &nbsp;&nbsp;&nbsp;"triplesFilter": ["det", "nsubj"],<br>
     * &nbsp;&nbsp;&nbsp;"textsPathToProcess": "E:\\Desarrollo\\TFG\\gutenberg\\downloads\\books\\prueba3",<br>
     * &nbsp;&nbsp;&nbsp;"totalThreads": 4,<br>
     * &nbsp;&nbsp;&nbsp;"stanfordOptions": {<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"-maxLength": "40",<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"-retainTmpSubcategories": null<br>
     * &nbsp;&nbsp;&nbsp;},<br>
     * &nbsp;&nbsp;&nbsp;"model": "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",<br>
     * &nbsp;&nbsp;&nbsp;"saveInDB": true,<br>
     * &nbsp;&nbsp;&nbsp;"adjustedFrequency": 0.0<br>
	 *}<p>El JSON recibido se mapea a un objeto de tipo ParamsAlgorithm.
	 * @param parameters parámetros de configuración
	 * @return JSON con los parámetros indicados si la ejecución es correcta, mensaje de error en caso contrario
	 * @see ParamsAlgorithm
	 */
	@Override
	public void executeAlgorithm(ParamsAlgorithm parameters) {
		collocatio.setAlgorithmConfig(parameters);
		collocatio.extractCollocations();
	}

	/**
	 * Obtiene una colocación en base a su identificador en la base de datos.
	 * @param id identificador de la coloación a consultar
	 * @param db nombre de la base de datos en la que consultar
	 * @return un objeto Collocatio que representa la colocación consultada
	 * @see Collocatio
	 */
	@Override
	public Collocatio findCollocation(long id, String db) {
		collocatioService.setDataBase(getDataBaseName(db));
		return collocatioService.findCollocationsById(id);
	}
	
	/**
	 * Ejecuta una consulta sobre la base de datos de colocaciones. La consulta ejecutada será del tipo indicado por el parámetro <i>queryType</i>.<p>
	 * Para realziar las consultas se utiliza internamente los servicios ofercidos por un objeto de tipo ICollocatioService.
	 * @param queryType tipo de consulta a realizar
	 * @param words lista de palabras a usar como filtro de la consulta
	 * @param offset página a obtener
	 * @param size  cantidad de registros de la página
	 * @param db nombre de la base de datos a la que consultar
	 * @see Collocatio
	 */
	@Override
	public List<Collocatio> findCollocations(String queryType, List<String> words, int offset, int size, String db) {
		List<Collocatio> listCollocatio = new ArrayList<Collocatio>();
		collocatioService.setDataBase(getDataBaseName(db));
		switch (queryType) 
		{
			case "by_words":
				listCollocatio = collocatioService.findCollocationsByWords(words, offset, size);
				break;
			case "start_with":
				listCollocatio = collocatioService.findCollocationsStartWith(words, offset, size);
				break;
			case "end_with":
				listCollocatio = collocatioService.findCollocationsEndWith(words, offset, size);
				break;
			case "all":
				if (offset == 0 && size == 0) {
					listCollocatio = collocatioService.findAllCollocations();
				} else {
					listCollocatio = collocatioService.findAllCollocationsPaged(offset, size);
				}
				break;
			case "best_mi":
				listCollocatio = collocatioService.findBestCollocationsByMutualInformation(size);
				break;				
			default:
				break;
		}
		return listCollocatio;
	}

	/**
	 * Obtiene una colocación en base a su identificador en la base de datos.
	 * @param id identificador de la coloación a consultar
	 * @param db nombre de la base de datos en la que consultar
	 * @return un objeto Collocatio que representa la colocación guardada
	 * @see Collocatio
	 */
	@Override
	public Collocatio addCollocation(Collocatio collocatio, String dbname) {
		collocatioService.setDataBase(getDataBaseName(dbname));
		long id = collocatioService.addCollocation(collocatio);
		Collocatio col = collocatio;
		col.setID(id);
		return col;
	}
	
	/**
	 * Elimina una colocación de la base de datos especificada.
	 * @param id identificador del registro a eliinar
	 * @param dbname base de datos donde guardar
	 * @return número de registros afectados
	 */
	@Override
	public long deleteCollocation(Long id, String dbname) {
		collocatioService.setDataBase(getDataBaseName(dbname));
		return collocatioService.deleteCollocation(id);
	}
	
	/**
	 * Ajusta el nombre de la base de datos que se ha pasado por parámetro. Si es null o no se ha establecido se usará la base de datos por defecto.
	 * @param db nombre de a base de datos que se ha pasado por parámetro
	 * @return nombre de la base de datos a utilizar
	 */
	private String getDataBaseName(String db) {
		return (db == null || db.isEmpty()) ? ConnectionFactory.DEFAULT_DB : db;
	}

	/**
	 * Elimina una base de datos.
	 * @param dbname identificador del registro a eliminar
	 */
	@Override
	public void deleteDataBase(String dbname) {
		collocatioService.deleteDataBase(dbname);
	}
}
