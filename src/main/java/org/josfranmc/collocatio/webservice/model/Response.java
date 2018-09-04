package org.josfranmc.collocatio.webservice.model;

import java.time.Instant;

import org.springframework.stereotype.Component;

/**
 * Respuesta que se devuelve a una petición web.<p>
 * Incluye una serie de campos que describen la respuesta:
 * <ul>
 * <li><b>timestamp</b>: fecha y hora de la respuesta</li>
 * <li><b>status</b>: código http devuelto</li>
 * <li><b>error</b>: mensaje de error</li>
 * <li><b>message</b>: mensaje descriptivo</li>
 * <li><b>path</b>: ruta del servicio web invocado</li>
 * </ul>
 * @author jose
 * @version 1.0
 */
@Component
public class Response {

	private Instant timestamp;
	
	private String status;
	
	private String error;
	
	private String message;
	
	private String path;
	
	
	public Response() {
		
	}

	public Instant getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Instant instant) {
		this.timestamp = instant;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
}
