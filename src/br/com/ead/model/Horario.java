package br.com.ead.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

@Entity
public class Horario {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Temporal(TemporalType.TIME)
	private Date manhaEntrada;
	
	@Column
	@Temporal(TemporalType.TIME)
	private Date manhaSaida;
	
	@Column
	@Temporal(TemporalType.TIME)
	private Date tardeEntrada;
	
	@Column
	@Temporal(TemporalType.TIME)
	private Date tardeSaida;
	
	@Column
	@Temporal(TemporalType.TIME)
	private Date tolerancia;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getManhaEntrada() {
		return manhaEntrada;
	}

	public void setManhaEntrada(Date manhaEntrada) {
		this.manhaEntrada = manhaEntrada;
	}

	public Date getManhaSaida() {
		return manhaSaida;
	}

	public void setManhaSaida(Date manhaSaida) {
		this.manhaSaida = manhaSaida;
	}

	public Date getTardeEntrada() {
		return tardeEntrada;
	}

	public void setTardeEntrada(Date tardeEntrada) {
		this.tardeEntrada = tardeEntrada;
	}

	public Date getTardeSaida() {
		return tardeSaida;
	}

	public void setTardeSaida(Date tardeSaida) {
		this.tardeSaida = tardeSaida;
	}
	

	public Date getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(Date tolerancia) {
		this.tolerancia = tolerancia;
	}
	
	

}
