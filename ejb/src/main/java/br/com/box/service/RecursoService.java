package br.com.box.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.box.dao.RecursoDAO;
import br.com.box.model.Recurso;
import br.com.box.util.Util;

@Named
@Stateless
public class RecursoService {

	@Inject
	private RecursoDAO recursoDAO;
	
	public List<Recurso> recuperarTodos(){
		return recursoDAO.listar();
	}
	
	public List<Recurso> recuperarTodosAtivos(){
		return recursoDAO.listarAtivos();
	}
	
	public List<Recurso> recuperarPorFiltro(Recurso recursoFiltro){
		return recursoDAO.buscarRecursosPorFiltros(recursoFiltro);
	}
	
	public void salvar(Recurso recurso){
		recursoDAO.salvar(recurso);
	}
	
	public void deletarLogico(Recurso recurso){
		recurso.setAtivo(Boolean.FALSE);
		recursoDAO.atualizar(recurso);
	}
	
	public void alterar(Recurso recurso){
		recursoDAO.atualizar(recurso);
	}
	
	public Recurso buscarPorPatrimonio(String patrimonio){
		return recursoDAO.buscarPor("numeroPatrimonio", patrimonio);
	}
	
	public Recurso buscarPorId(Long id){
		return recursoDAO.buscar(id);
	}
	
	public List<Recurso> listarRecursos(Recurso recurso) {
		if (!Util.ObjectIsNull(recurso.getNumeroPatrimonio()) && !Util.ObjectIsNull(recurso.getAtivo())){
			return this.recuperarPorFiltro(recurso);
		}else{
			return this.recuperarTodosAtivos();
		}
	}
	
}
