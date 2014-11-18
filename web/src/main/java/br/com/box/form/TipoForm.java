package br.com.box.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.box.model.Tipo;
import br.com.box.qualifier.TipoBean;

@Named
public class TipoForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Tipo> listaTipos = new ArrayList<Tipo>();
	
	@Inject
	@TipoBean
	private Tipo tipo;

	public List<Tipo> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<Tipo> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
