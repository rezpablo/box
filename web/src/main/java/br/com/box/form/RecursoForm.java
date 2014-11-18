package br.com.box.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.box.model.Recurso;
import br.com.box.model.Tipo;
import br.com.box.qualifier.RecursoBean;

@Named
public class RecursoForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	@RecursoBean
	private Recurso recurso;
	
	@Inject
	@RecursoBean
	private Recurso recursoFiltro;
	
	private List<Recurso> recursos = new ArrayList<Recurso>();
	private List<Tipo> tipos = new ArrayList<Tipo>();

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}

	public Recurso getRecursoFiltro() {
		return recursoFiltro;
	}

	public void setRecursoFiltro(Recurso recursoFiltro) {
		this.recursoFiltro = recursoFiltro;
	}
	

}
