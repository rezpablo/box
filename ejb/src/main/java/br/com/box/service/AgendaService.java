package br.com.box.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import br.com.box.dao.AgendaDAO;
import br.com.box.exception.AgendamentoNaoDisponivelException;
import br.com.box.exception.DataInicioMaiorDataFimException;
import br.com.box.model.Agenda;

import com.google.common.collect.Iterables;

@Named
@Stateless
public class AgendaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	AgendaDAO agendaDAO;
	
	public List<Agenda> listar(){
		return agendaDAO.listar();
	}

	public Agenda buscarPorId(Long idLong) {
		return agendaDAO.buscar(idLong);
	}
	
	public void salvar (Agenda agenda) throws AgendamentoNaoDisponivelException, DataInicioMaiorDataFimException{
		if(agenda.getDataFim().before(agenda.getDataInicio())){
			throw new DataInicioMaiorDataFimException("Data fim maior que início!");
		}
		else if(verificaDisponibilidadeAgendamento(agenda)) {
			if(agenda.getId()==null){
				agenda = agendaDAO.salvarERetornar(agenda);			
			}else{
				agendaDAO.atualizar(agenda);
			}
		} else {
			throw new AgendamentoNaoDisponivelException("Não foi possivel agendar pois o recurso não está disponível");
		}
		
	}

	public List<Agenda> filtrarAgenda(Agenda agendaFiltro) {		
		return agendaDAO.buscarAgendasPorFiltros(agendaFiltro);
	}
	
	public void deletar(Agenda agenda){
		agendaDAO.delete(agenda);
	}
	
	public boolean verificaDisponibilidadeAgendamento(Agenda agenda){
		List<Agenda> listaAgendasPorRecurso = agendaDAO.buscarAgendasPorRecursoDataAgendamento(agenda); 
		if(Iterables.isEmpty(listaAgendasPorRecurso)){
			return true;
		}
		return false;
	}
	
}
