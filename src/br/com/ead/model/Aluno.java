package br.com.ead.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Aluno {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=250)
	private String nome;
	@Column(length=20)
	private String matricula;
	@ManyToMany(mappedBy="alunos")
	private List<Turma> turmas;
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
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	
	public void preencherUsuario(List<Role> roles) {
		this.usuario.setNome(nome);
		this.usuario.setMainUser(false);
		this.usuario.setEnabled(true);
		this.usuario.setRoles(roles);
		this.usuario.setPassword("123456");
	}
}
