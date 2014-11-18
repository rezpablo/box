package br.com.box.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.omnifaces.cdi.ViewScoped;

import br.com.box.form.GrupoForm;
import br.com.box.model.Grupo;
import br.com.box.service.GrupoService;
import br.com.box.util.FacesUtil;
import br.com.box.util.InterceptadorExcecaoController;

import com.google.common.collect.Iterables;

@Named
@ViewScoped
@Interceptors(InterceptadorExcecaoController.class)
public class GrupoController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String URLCONSULTA = "/pages/protected/grupo/consultar.jsf";

	@Inject
	private GrupoService grupoService;

	@Inject
	private GrupoForm grupoForm;

	public String gravar() {
		grupoService.gravar(grupoForm.getGrupo());
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}

	public String alterar() {
		grupoService.alterar(grupoForm.getGrupo());
		FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
		return URLCONSULTA + "?faces-redirect=true";
	}

	public List<Grupo> getGrupos() throws Exception {
		if (Iterables.isEmpty(grupoForm.getGrupos())) {
			this.atualizarListaGrupo();
		}
		return grupoForm.getGrupos();
	}

	private void atualizarListaGrupo() throws Exception {
		grupoForm.setGrupos(grupoService.recuperarTodos());
	}

	public void deletar(Grupo grupo) throws Exception {
		grupoService.deletar(grupo);
		FacesUtil.mostrarMensagemSucesso("deletado.sucesso");
		this.atualizarListaGrupo();
	}

	public GrupoForm getGrupoForm() {
		return grupoForm;
	}

	public void setGrupoForm(GrupoForm grupoForm) {
		this.grupoForm = grupoForm;
	}
	
}
