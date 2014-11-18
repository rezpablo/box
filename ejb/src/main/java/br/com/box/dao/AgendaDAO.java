package br.com.box.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.box.model.Agenda;
import br.com.box.util.Util;

import com.google.common.base.Strings;

public class AgendaDAO extends GenericDAO<Agenda>{

	public List<Agenda> buscarAgendasPorFiltros(Agenda agendaFiltro) {
		StringBuilder str = new StringBuilder("SELECT a FROM Agenda a WHERE 1=1 ");
		if(!Util.ObjectIsNull(agendaFiltro.getDataInicio())){
			str.append(" AND a.dataInicio >= :dataInicio");
		}
		if(!Util.ObjectIsNull(agendaFiltro.getDataFim())){
			str.append(" AND a.dataFim <= :dataFim");
		}
		if(!Util.ObjectIsNull(agendaFiltro.getRecurso()) && !Util.ObjectIsNull(agendaFiltro.getRecurso().getId())){
			str.append(" AND a.recurso.id = :idRecurso");
		}
		if(!Util.ObjectIsNull(agendaFiltro.getPessoa()) && !Util.ObjectIsNull(agendaFiltro.getPessoa().getId())){
			str.append(" AND a.pessoa.id = :idPessoa");
		}		
		if(!Strings.isNullOrEmpty(agendaFiltro.getTituloEvento())){
			str.append(" AND LOWER(a.tituloEvento) LIKE :titulo");
		}
		Query query = getEntityManager().createQuery(str.toString());
		
		query = setParameters(query, agendaFiltro);
		
		return query.getResultList();
	}
	
	public List<Agenda> buscarAgendasPorRecursoDataAgendamento(Agenda agenda){
		String hql = "SELECT a FROM Agenda a WHERE recurso.id = :idRecurso AND ((a.dataInicio BETWEEN :dataInicio AND :dataFim) OR (a.dataFim BETWEEN :dataInicio AND :dataFim))";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("idRecurso", agenda.getRecurso().getId());
		query.setParameter("dataInicio", agenda.getDataInicio());
		query.setParameter("dataFim", agenda.getDataFim());
		return query.getResultList();
	}
	
	private Query setParameters(Query query, Agenda agendaFiltro){
		if(!Util.ObjectIsNull(agendaFiltro.getDataInicio())){
			query.setParameter("dataInicio", agendaFiltro.getDataInicio());
		}
		if(!Util.ObjectIsNull(agendaFiltro.getDataFim())){
			query.setParameter("dataFim", agendaFiltro.getDataFim());
		}
		if(!Util.ObjectIsNull(agendaFiltro.getRecurso()) && !Util.ObjectIsNull(agendaFiltro.getRecurso().getId())){
			query.setParameter("idRecurso", agendaFiltro.getRecurso().getId());
		}
		if(!Util.ObjectIsNull(agendaFiltro.getPessoa()) && !Util.ObjectIsNull(agendaFiltro.getPessoa().getId())){
			query.setParameter("idPessoa", agendaFiltro.getPessoa().getId());
		}		
		if(!Strings.isNullOrEmpty(agendaFiltro.getTituloEvento())){
			query.setParameter("titulo", "%"+agendaFiltro.getTituloEvento().toLowerCase()+"%");
		}
		return query;
	}
	
	

}
