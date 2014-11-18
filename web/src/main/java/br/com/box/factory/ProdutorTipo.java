package br.com.box.factory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.google.common.base.Strings;

import br.com.box.model.Tipo;
import br.com.box.qualifier.TipoBean;
import br.com.box.service.TipoService;
import br.com.box.util.FacesUtil;

public class ProdutorTipo {

	@Inject
	TipoService tipoService;
	
	@Produces
	@TipoBean
	public Tipo produzirTipo(){
		Tipo tipo = new Tipo();
		String id = FacesUtil.getRequestParameter("idTipo");
		if(!Strings.isNullOrEmpty(id)){
			Long idLong = new Long(id);
			return tipoService.buscarPorId(idLong);
		}
		return tipo;
	}
}
