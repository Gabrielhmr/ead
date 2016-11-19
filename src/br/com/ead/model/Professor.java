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
	
	@Column(unique = true, length=14)
	private String cartao;
	
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCartao() {
		return cartao;
	}
	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
//	public Usuario getUsuario() {
//		return usuario;
//	}
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//	public void preencherUsuario(List<Role> roles) {
//		this.usuario.setNome(nome);
//		this.usuario.setMainUser(false);
//		this.usuario.setEnabled(true);
//		this.usuario.setRoles(roles);
//		this.usuario.setPassword("123456");
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		//result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
//		if (usuario == null) {
//			if (other.usuario != null)
//				return false;
//		} else if (!usuario.equals(other.usuario))
//			return false;
		return true;
	}
	
	
	
}
