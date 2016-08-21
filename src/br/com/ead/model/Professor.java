package br.com.ead.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Professor {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=300)
	private String nome;
	@OneToOne(cascade=CascadeType.ALL)
	private Usuario usuario = new Usuario();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void preencherUsuario(List<Role> roles) {
		this.usuario.setNome(nome);
		this.usuario.setMainUser(false);
		this.usuario.setEnabled(true);
		this.usuario.setRoles(roles);
		this.usuario.setPassword("123456");
	}
	
	
}
