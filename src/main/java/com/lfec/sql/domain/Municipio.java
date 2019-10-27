package com.lfec.sql.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Municipio{

	@Column(length = 7)
	private Integer codigo;

	@Column(length = 60)
	private String nome;

	public Municipio() {
		super();
	}

	public Municipio(Integer codigo, String nome) {
		this();
		this.codigo = codigo;
		this.nome = nome;
	}

	public Integer getCodigo() {

		return codigo;
	}

	public void setCodigo(Integer codigo) {

		this.codigo = codigo;
	}

	public String getNome() {

		return nome;
	}

	public void setNome(String nome) {

		this.nome = nome;
	}


}
