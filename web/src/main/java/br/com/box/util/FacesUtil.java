package br.com.box.util;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Classe utilitária para desenvolvimento JSF
 */
public final class FacesUtil {
	
	private static ResourceBundle resourceBundle;
	
	private FacesUtil() {
		
	}
	
   
	public static String getRequestParameter(String name) {
		return (String)getExternalContext().getRequestParameterMap().get(name);
	}

	public static void mostrarMensagemSucesso(String keyMessage) {
		mostrarMensagem(FacesMessage.SEVERITY_INFO, keyMessage);
	}

	public static void mostrarMensagemAlerta(String keyMessage) {
		mostrarMensagem(FacesMessage.SEVERITY_WARN, keyMessage);
	}
	
	public static void mostrarMensagemErro(String keyMessage) {
		mostrarMensagem(FacesMessage.SEVERITY_ERROR, keyMessage);
	}
	
	private static void mostrarMensagem(FacesMessage.Severity severity, String keyMessage) {
		FacesMessage facesMessage = new FacesMessage(severity, getMessage(keyMessage), "");
		getContext().addMessage(null, facesMessage);
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public static Map getSessionMap() {
		return getExternalContext().getSessionMap();
	}
	
	public static ServletContext getServletContext() {
		return (ServletContext)getExternalContext().getContext();
	}
	
	public static HttpServletRequest getServletRequest() {
		return (HttpServletRequest)getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse)getExternalContext().getResponse();
	}
	
	private static ResourceBundle getResourceBundle(){
		if(resourceBundle == null) {
			Locale locale = getContext().getViewRoot().getLocale();
			resourceBundle = ResourceBundle.getBundle("messages", locale);
		}
		
		return resourceBundle;
	}
	
	public static String getMessage(String key) {
		return getResourceBundle().getString(key);
	}
	
	public static HttpSession getHttpSession() {
		return (HttpSession) FacesUtil.getExternalContext().getSession(false);
	}
	
	private static FacesContext getContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static void mostrarMensagemRedirect(String key){
		ExternalContext externalContext = getContext().getExternalContext();
		externalContext.getFlash().put("messageRedirect", getMessage(key));
		externalContext.getFlash().setKeepMessages(true);
	}
	
	public static void redirect(String retorno) throws IOException {
		 getExternalContext().redirect(retorno);
	}
	
	public static String gerarUrl(String url) {
		return  getContextName().concat(url);
	}
	
	public static String getContextName() {
		return getServletRequest().getContextPath();
	}
	
}
