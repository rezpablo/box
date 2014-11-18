package br.com.box.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Iterables;

import br.com.box.dao.RecursoDAO;
import br.com.box.dao.TipoDAO;
import br.com.box.model.Recurso;
import br.com.box.model.Tipo;

@Named
@Stateless
public class TipoService {

	@Inject
	private TipoDAO tipoDAO;
	
	@Inject RecursoDAO recursoDAO;
	
	public List<Tipo> listar(){
		return tipoDAO.listar();
	}

	public Tipo buscarPorId(Long idLong) {
		return tipoDAO.buscar(idLong);
	}
	
	public void deletar(Tipo tipo) {
		tipoDAO.delete(tipo);
	}
	
	public Tipo buscarPorNome(String nome){
		return tipoDAO.buscarPorNome(nome);
	}
	
	public void gravar(Tipo tipo){
		tipoDAO.salvar(tipo);
	}
	
	public void alterar(Tipo tipo){
		tipoDAO.atualizar(tipo);
	}

	public boolean possuiRelacionamento(Tipo tipo) {
		List<Recurso>listaRecursos = recursoDAO.listarPor("tipo.id", tipo.getId());
		if(Iterables.isEmpty(listaRecursos)){
			return false;
		}else{
			return true;
		}
	}
}
