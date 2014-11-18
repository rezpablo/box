package br.com.box.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.omnifaces.cdi.ViewScoped;

import br.com.box.form.RecursoForm;
import br.com.box.model.Recurso;
import br.com.box.model.Tipo;
import br.com.box.service.RecursoService;
import br.com.box.service.TipoService;
import br.com.box.util.FacesUtil;
import br.com.box.util.InterceptadorExcecaoController;
import br.com.box.util.Util;

import com.google.common.collect.Iterables;

@Named
@ViewScoped
@Interceptors(InterceptadorExcecaoController.class)
public class RecursoController implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String URLCONSULTA = "/pages/protected/recurso/consultar.jsf";	
	
	@Inject
	private RecursoForm recursoForm;
	
	@Inject
	private RecursoService recursoService;
	
	@Inject 
	private TipoService tipoService;

	public String gravar(){
		recursoService.salvar(recursoForm.getRecurso());
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}
	
	public List<Recurso> getRecursos(){
		if (Iterables.isEmpty(recursoForm.getRecursos())){
			this.atualizarListaRecursos();
		}		
		return recursoService.listarRecursos(recursoForm.getRecursoFiltro());
	}
	
	public void filtrarRecursos() {		
		recursoForm.setRecursoFiltro(recursoForm.getRecurso());		
	}

	private void atualizarListaRecursos() {
		recursoForm.setRecursos(recursoService.recuperarTodos());
	}
	
	public void deletar(Recurso recurso){
		recursoService.deletarLogico(recurso);
		FacesUtil.mostrarMensagemSucesso("deletado.sucesso");
		this.atualizarListaUsuarios();
	}
	
	private void atualizarListaUsuarios(){
		recursoForm.setRecursos(recursoService.recuperarTodos());
	}
	
	public List<Tipo> getTipos(){
		if (Iterables.isEmpty(recursoForm.getTipos())){
			recursoForm.setTipos(tipoService.listar());
		}
		return recursoForm.getTipos();
	}
	
	public String alterar() throws Exception{
		recursoService.alterar(recursoForm.getRecurso());		
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}
	
	public RecursoForm getRecursoForm() {
		return recursoForm;
	}

	public void setRecursoForm(RecursoForm recursoForm) {
		this.recursoForm = recursoForm;
	}

	
	
}
