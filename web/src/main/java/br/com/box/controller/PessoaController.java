package br.com.box.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;


@RequestScoped
@Named
public class PessoaController {
	private static final Logger LOGGER = Logger.getLogger(PessoaController.class);

	public void teste(){
		LOGGER.info("Logger Funcionando");
		
		
	}
	
	
}
