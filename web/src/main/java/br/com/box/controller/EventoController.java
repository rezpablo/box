package br.com.box.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.box.exception.AgendamentoNaoDisponivelException;
import br.com.box.exception.DataInicioMaiorDataFimException;
import br.com.box.facade.AgendaFacade;
import br.com.box.form.EventoForm;
import br.com.box.model.Agenda;
import br.com.box.model.Pessoa;
import br.com.box.model.Recurso;
import br.com.box.service.AgendaService;
import br.com.box.service.PessoaService;
import br.com.box.service.RecursoService;
import br.com.box.util.FacesUtil;

import com.google.common.collect.Iterables;
 
@ManagedBean
@ViewScoped
public class EventoController implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Inject
	private EventoForm eventoForm;
	
	@Inject
	private AgendaService agendaService;
	
	@Inject
	private RecursoService recursoService;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject AgendaFacade agendaFacade;
		
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private boolean novoRegistro;
    private static final Integer ZERO =  0;
    
    
    public ScheduleModel getEventos(){
    	
    	if (Iterables.isEmpty(eventoForm.getEventModel().getEvents())) {
    		preencherAgenda(false);
		}
    	
    	return eventoForm.getEventModel(); 
    }
    
    public List<Agenda> getAgendas(){
    	if(Iterables.isEmpty(eventoForm.getListaAgendas())){
    		atualizarListaAgendas();
    	}
    	return eventoForm.getListaAgendas();
    }
    
    private void atualizarListaAgendas() {
    	eventoForm.setListaAgendas(agendaService.listar());
	}
      
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }   
 
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
    
    public void gravar(ActionEvent actionEvent) {
    	try {
	    	Agenda agenda =(Agenda) event.getData();
	    	acrescentarNotificacao(agenda);
	    	agendaFacade.salvarAgenda((Agenda) event.getData());
	    	preencherAgenda(true);
	    	FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
    	} catch(AgendamentoNaoDisponivelException e) {
    		FacesUtil.mostrarMensagemAlerta("erro.existe.evento.para.este.recurso");
    	} catch (DataInicioMaiorDataFimException e) {
			FacesUtil.mostrarMensagemAlerta("erro.datafim.maior.datainicio");
		}
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    	event = (ScheduleEvent) selectEvent.getObject();
    	event.getId();
    	setNovoRegistro(true);
        
    }
    
    public void apagar(ActionEvent actionEvent) {
    	agendaService.deletar((Agenda) event.getData());
    	preencherAgenda(true);
    	FacesUtil.mostrarMensagemSucesso("cadastro.sucesso");
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
    	Agenda agenda = new Agenda();
    	agenda.setDataInicio((Date) selectEvent.getObject());
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(), agenda);
        event.getId();
    }
     
    
   
    
    public void onEventMove(ScheduleEntryMoveEvent event) {
		try {
			Agenda agenda = (Agenda) event.getScheduleEvent().getData();
			/*agendaService.salvar(agenda);*/
			agendaFacade.salvarAgenda(agenda);
			preencherAgenda(false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento Movido", "Dia:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
			addMessage(message);
		} catch (AgendamentoNaoDisponivelException e) {	
    		FacesUtil.mostrarMensagemAlerta("Não foi possivel alterar este evento");
		}catch(DataInicioMaiorDataFimException e){
			FacesUtil.mostrarMensagemAlerta("erro.datafim.maior.datainicio");
		}
	}
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
    	try {			
	        Agenda agenda = (Agenda) event.getScheduleEvent().getData();
	        /*agendaService.salvar(agenda);*/
	        agendaFacade.salvarAgenda(agenda);
	        preencherAgenda(false);
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento Ajustado", "Dia:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
	        addMessage(message);
    	} catch (Exception e) {
    		FacesUtil.mostrarMensagemAlerta("Não foi possivel alterar este evento");
		}
    }
    
    public List<Recurso> getRecursos(){
    	if(Iterables.isEmpty(eventoForm.getListaRecursos())){
    		eventoForm.setListaRecursos(recursoService.recuperarTodos());
    	}
    	return eventoForm.getListaRecursos();
    }
    
    public List<Pessoa> getPessoas(){
    	if(Iterables.isEmpty(eventoForm.getListaPessoas())){
    		eventoForm.setListaPessoas(pessoaService.recuperarTodos());
    	}
    	return eventoForm.getListaPessoas();
    }
    
    public void filtrarAgenda(){
    	eventoForm.setListaAgendas(agendaService.filtrarAgenda(eventoForm.getAgendaFiltro()));
    	if(Iterables.isEmpty(eventoForm.getListaAgendas())){
    		FacesUtil.mostrarMensagemAlerta("erro.mensagem.buscarPorFiltro");
    	}
    	preencherAgenda(false);
    }
    
    private void acrescentarNotificacao(Agenda agenda){
    	if(ZERO.intValue() != eventoForm.getNotificacaoMinutos().intValue()){
	    	DateTime dataNotificacao;
	    	DateTime dataInicio = new DateTime(agenda.getDataInicio());
	    	dataNotificacao = new DateTime(dataInicio.minusMinutes(eventoForm.getNotificacaoMinutos()));
	    	agenda.setDataNotificacao(dataNotificacao.toDate());
    	}
    }
    
	private void preencherAgenda(boolean limparListaAgendas) {
		eventoForm.getEventModel().clear();
		if(limparListaAgendas){
			eventoForm.getListaAgendas().clear();
		}
    	for(Agenda eventoAgenda : this.getAgendas()){
        	eventoForm.getEventModel().addEvent(new DefaultScheduleEvent(eventoAgenda.getDescricaoEvento().concat(" - ").concat(eventoAgenda.getRecurso().getDescricao()), eventoAgenda.getDataInicio(), eventoAgenda.getDataFim(), eventoAgenda));
    	}
	}
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public EventoForm getEventoForm() {
		return eventoForm;
	}

	public void setEventoForm(EventoForm eventoForm) {
		this.eventoForm = eventoForm;
	}

	public boolean isNovoRegistro() {
		return novoRegistro;
	}

	public void setNovoRegistro(boolean novoRegistro) {
		this.novoRegistro = novoRegistro;
	}

	
}
