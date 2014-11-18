package br.com.box.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.box.dao.PessoaDAO;
import br.com.box.model.Pessoa;

@Named
@Stateless
public class PessoaService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	PessoaDAO pessoaDao;
	
	public List<Pessoa> recuperarTodos() {
		return pessoaDao.listar();
	}
	
}
