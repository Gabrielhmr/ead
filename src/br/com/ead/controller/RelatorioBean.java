package br.com.ead.controller;

import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RelatorioBean {
	
	private Date inicio;
	private Date fim;
	private String nome;
	
	
	public void gerarRelatorio(){
		
		System.out.println(getInicio());
		System.out.println(getFim());
		System.out.println(getNome());
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
