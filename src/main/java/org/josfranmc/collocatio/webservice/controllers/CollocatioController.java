package org.josfranmc.collocatio.webservice.controllers;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.josfranmc.collocatio.algorithms.ParamsAlgorithm;
import org.josfranmc.collocatio.service.QueryType;
import org.josfranmc.collocatio.service.domain.Collocatio;
import org.josfranmc.collocatio.webservice.model.Response;
import org.josfranmc.collocatio.webservice.services.CollocatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que gestiona todoas las peticiones relacionadas con las colocaciones: ejecución de algoritmos de extracción y consultas.
 * @author Jose Francisco Mena Ceca
 * @version 1.0
 * @see CollocatioService
 * @see ParamsAlgorithm
 * @see QueryType
 */
@RestController
@CrossOrigin
@RequestMapping("/collocatio")
public class CollocatioController {

	@Autowired
	private CollocatioService collocatioService;

	/**
	 * Respuesta a devolver a las peticiones realizadas
	 */
	@Autowired
	private Response response;
	
	
	/**
	 * @return un JSON con los valores por defecto de los parámetros que se pueden utilizar a la hora de configurar un algoritmo para la extracción de colocaciones
	 * @see ParamsAlgorithm
	 */
	@RequestMapping(value = "/extract/info_parameters", method = RequestMethod.GET)
	public ResponseEntity<ParamsAlgorithm> getDefaultParameters() {
		return new ResponseEntity<ParamsAlgorithm>(new ParamsAlgorithm(), HttpStatus.OK);
	}	
	
	/**
	 * Ejecuta un algoritmo para realizar la extracción de colocaciones.<p>Recibe un JSON con los parámetros de configuración, el cual debe tener
	 * la siguiente estructura (los valores de cada parámetro son a modo de ejemplo):<p>
	 * {<br>
     * "algorithmType": "MUTUAL_INFORMATION",<br>
     * "triplesFilter": [],<br>
     * "textsPathToProcess": "C:\\ruta\textos\\extracción\\",<br>
     * "totalThreads": 4,<br>
     * "stanfordOptions": {<br>
     * &nbsp;&nbsp;"-maxLength": "35",<br>
     * &nbsp;&nbsp;"-retainTmpSubcategories": null<br>
     *},<br>
     * "model": "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",<br>
     * "saveInDB": true,<br>
     * "adjustedFrequency": 0<br>
	 *}<p>El JSON recibido se mapea a un objeto de tipo ParamsAlgorithm
	 * @param parameters parámetros de configuración
	 * @return un JSON con el resultado del proceso
	 * @see ParamsAlgorithm
	 */
	@RequestMapping(value = "/extract", method = RequestMethod.POST)
	public ResponseEntity<Response> executeAlgorithm(@RequestBody ParamsAlgorithm parameters) {
		collocatioService.executeAlgorithm(parameters);
		
		response.setPath("/collocatio/extract");
		response.setStatus(HttpStatus.OK.toString());
		response.setMessage("Extracción realizada");
		response.setError(null);
		response.setTimestamp(getCurrentTime());
		
		return  new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Consulta la colocación identificada por {id}
	 * @param id identificador de la coloación a consultar
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return la colocación cuyo identificador se ha indicado
	 * @see Collocatio
	 */
	@ResponseBody
	@RequestMapping(value = "/find/id/{id}", method = RequestMethod.GET)
	public Collocatio findCollocationById(@PathVariable("id") long id, 
			                              @RequestParam(value="db", required=false) String db)
	{
		return collocatioService.findCollocation(id, db);
	}
	
	/**
	 * Ejecuta un tipo de consulta para recuperar aquellas colocaciones que cumplan un criterio determinado.
	 * @param queryType tipo de consulta a realizar
	 * @param words lista de palabras que conforman los criterios de búsqueda
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return una lista con las colocaciones recuperadas
	 * @see QueryType
	 * @see Collocatio
	 */
	@ResponseBody
	@RequestMapping(value = "/find/{query_type}", method = RequestMethod.GET)
	public List<Collocatio> findByWords(@PathVariable("query_type") String queryType, 
			                            @RequestParam(value="words", required=false) List<String> words,
			                            @RequestParam(value="offset", defaultValue="0") int offset,
			                            @RequestParam(value="size", defaultValue="0") int size,
			                            @RequestParam(value="db", required=false) String db)
	{
		return collocatioService.findCollocations(queryType, words, offset, size, db);
	}

	/**
	 * Devuelve todas las colocaciones existentes en la base de datos.<br>
	 * Se puede paginar el resultado, de forma que se puede obtener una página concreta.
	 * @param offset página que devolver
	 * @param size número de registros por página
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return una lista con las colocaciones recuperadas
	 */
	@ResponseBody
	@RequestMapping(value = "/find/all", method = RequestMethod.GET)
	public List<Collocatio> findAll(@RequestParam(value="offset", defaultValue="0") int offset,
                                    @RequestParam(value="size", defaultValue="0") int size,
                                    @RequestParam(value="db", defaultValue="col_default") String db)
	{
		return collocatioService.findCollocations("all", null, offset, size, db);
	}
	
	/**
	 * Añade una nueva colocación a la base de datos.
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return un JSON con el resultado del proceso
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Collocatio> addCollocatio(Collocatio collocatio, 
			                                        @RequestParam(value="db", required=false) String db)
	{
		Collocatio col = collocatioService.addCollocation(collocatio, db);
		return new ResponseEntity<Collocatio>(col, HttpStatus.OK);
	}	
	
	/**
	 * Elimina una colocación de la base de datos.
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return un JSON con el resultado del proceso
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteCollocatio(@PathVariable("id") Long id, 
			                                         @RequestParam(value="db", required=false) String dbname)
	{
		String status = null;
		String message = null;
		
		long result = collocatioService.deleteCollocation(id, dbname);
		
		if (result > 0) {
			status = HttpStatus.OK.toString();
			message = "Eliminación realizada";
		} else {
			status = HttpStatus.OK.toString();
			message = "Ningún cambio realizado";
		}
		
		response.setPath("/collocatio/delete/" + id);
		response.setStatus(status);
		response.setMessage(message);
		response.setError(null);
		response.setTimestamp(getCurrentTime());

		return  new ResponseEntity<Response>(response, HttpStatus.OK);
	}	

	/**
	 * Elimina una colocación de la base de datos.
	 * @param db nombre de la base de datos a consultar (por defecto se usa col_default)
	 * @return un JSON con el resultado del proceso
	 */
	@ResponseBody
	@RequestMapping(value = "/db/delete/{dbname}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteDataBase(@PathVariable("dbname") String dbname) {

		collocatioService.deleteDataBase(dbname);

		response.setPath("/collocatio/db/delete/" + dbname);
		response.setStatus(HttpStatus.OK.toString());
		response.setMessage("Proceso realizado");
		response.setError(null);
		response.setTimestamp(getCurrentTime());

		return  new ResponseEntity<Response>(response, HttpStatus.OK);
	}	
	
	private Instant getCurrentTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.toInstant();
	}
}