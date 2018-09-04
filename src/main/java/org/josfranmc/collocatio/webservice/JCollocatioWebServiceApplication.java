package org.josfranmc.collocatio.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.josfranmc.collocatio.webservice")
@SpringBootApplication
public class JCollocatioWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JCollocatioWebServiceApplication.class, args);
	}
}