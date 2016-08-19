package br.com.ead.validacao.serial;

import java.io.Serializable;

public class Serial implements Serializable {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
