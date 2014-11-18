package br.com.box.dao;

import javax.ejb.Stateless;

import br.com.box.model.Tipo;

@Stateless
public class TipoDAO extends GenericDAO<Tipo>{
	
	public Tipo buscarPorNome(String nome){
		return buscarPor("nome", nome);
	}

}
