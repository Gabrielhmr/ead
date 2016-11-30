package br.com.ead.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import br.com.ead.dao.RelatorioDao;
import br.com.ead.service.GeradorRelatorio;
import br.com.ead.util.FacesUtils;

@ManagedBean
public class RelatorioBean {
	
	private Date inicio = new Date();
	private Date fim = new Date();
	
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	
	public String gerarRelatorio() throws IOException, JRException{

		ExternalContext context =  FacesContext.getCurrentInstance().getExternalContext();
		
		ServletContext servletContext = (ServletContext) context.getContext();	
		String file = servletContext.getRealPath("/WEB-INF/jasper/acesso.jasper");
	    
	    Map<String, Object> parametros = new HashMap<String, Object>();
	    parametros.put("DATA_INI",inicio);
	    parametros.put("DATA_FIM",fim);
	    
	    Connection connexao = new RelatorioDao().getConnection();
	    
	    GeradorRelatorio geradorRelatorio = new GeradorRelatorio(file, parametros, connexao);
	    
	    if(geradorRelatorio.isEmpty()){
	    	facesUtils.adicionaMensagemDeErro("Nenhum registro encontrado para o periodo abaixo");
	    	Flash flash = context.getFlash();
	    	flash.setKeepMessages(true);
	    	return "/pages/relatorio/frequencia.xhtml?faces-redirect=true";
	    	
	    }else{
	    	HttpServletResponse response = (HttpServletResponse)   context.getResponse();
	    	geradorRelatorio.geraPDFParaOutputStream(response.getOutputStream());
		    return null;
	    }
	    
	}
	
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}
	
}
