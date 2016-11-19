package br.com.ead.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Aluno {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length=14)
	private String cartao;
	
	@Column(length=250)
	private String nome;
	
	@Column(length=20)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name="turma_id")
	private Turma turma;
	
	@Column
	private boolean habilitado = true;
	
	//@OneToOne(cascade=CascadeType.ALL)
	//private Usuario usuario = new Usuario();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
//	public Usuario getUsuario() {
//		return usuario;
//	}
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
//	public void preencherUsuario(List<Role> roles) {
//		this.usuario.setNome(nome);
//		this.usuario.setMainUser(false);
//		this.usuario.setEnabled(true);
//		this.usuario.setRoles(roles);
//		this.usuario.setPassword("123456");
//	}
}
