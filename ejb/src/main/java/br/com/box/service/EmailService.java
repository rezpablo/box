package br.com.box.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

import br.com.box.model.Notificacao;

@Singleton
public class EmailService {
	
	private static Logger log = Logger.getLogger(EmailService.class);
	
	@Inject
	private NotificacaoService notificacaoService;
	
	@Resource(mappedName = "java:jboss/mail/box")	
    private Session mailSession;
	
	@Schedule(hour="*", minute = "*", persistent=false )
	public void executarTarefa(){
		for (Notificacao notificacao : notificacaoService.notificacoesPendentes()){
			try {
				enviarEmailNotificacao(notificacao);
				atualizarNotificacaoEnviada(notificacao);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	private void enviarEmailNotificacao(Notificacao notificacao) throws AddressException, MessagingException {
		MimeMessage mail = gerarMensagemNotificacao(notificacao);				
		Transport.send(mail);
	}

	private MimeMessage gerarMensagemNotificacao(Notificacao notificacao)
			throws AddressException, MessagingException {
		MimeMessage mail = new MimeMessage(mailSession);
		Address to = new InternetAddress(notificacao.getDestinatario());
		mail.setRecipient(Message.RecipientType.TO, to);
		mail.setSubject("Cadastro");
		mail.setSentDate(new Date());
		mail.setContent(notificacao.getCorpo(), "text/html");
		return mail;
	}

	private void atualizarNotificacaoEnviada(Notificacao notificacao) {
		notificacao.setDataEnvio(new Date());
		notificacaoService.atualizar(notificacao);
	}	
	
}
