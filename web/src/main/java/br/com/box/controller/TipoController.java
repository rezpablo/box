package br.com.box.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.omnifaces.cdi.ViewScoped;

import br.com.box.form.TipoForm;
import br.com.box.model.Tipo;
import br.com.box.service.TipoService;
import br.com.box.util.FacesUtil;
import br.com.box.util.InterceptadorExcecaoController;

import com.google.common.collect.Iterables;

@Named
@ViewScoped
@Interceptors(InterceptadorExcecaoController.class)
public class TipoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String URLCONSULTA = "/pages/protected/tipo/consultar.jsf";	
	
	@Inject
	private TipoService tipoService;
	
	@Inject
	private TipoForm tipoForm;	

	public List<Tipo> getTipos(){
		if(Iterables.isEmpty(tipoForm.getListaTipos())){
			atualizarListaTipos();
		}
		return tipoForm.getListaTipos();
	}
	
	public String gravar(){
		tipoService.gravar(tipoForm.getTipo());
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}
	
	public String alterar(){
		tipoService.alterar(tipoForm.getTipo());
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}
	
	public void deletar(Tipo tipo){
		if(!tipoService.possuiRelacionamento(tipo)){
			tipoService.deletar(tipo);
			FacesUtil.mostrarMensagemSucesso("deletado.sucesso");
			atualizarListaTipos();
		}else{
			FacesUtil.mostrarMensagemAlerta("erro.mensagem.deletar.registro.associado");
		}
	}
	
	private  void atualizarListaTipos(){
		tipoForm.setListaTipos(tipoService.listar());
	}
	
	public TipoForm getTipoForm() {
		return tipoForm;
	}

	public void setTipoForm(TipoForm tipoForm) {
		this.tipoForm = tipoForm;
	}
}
