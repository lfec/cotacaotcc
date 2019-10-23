package com.lfec.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cotacao_Regiao")
public class Regiao extends Entidade{

	private String nome;

	private String descricao;

	@ElementCollection
	@CollectionTable(name = "cotacao_Regiao_Municipio")
	private List<Municipio> municipioRegiaoList;

	public Regiao() {

		super();
		this.municipioRegiaoList = new ArrayList<Municipio>();
	}

	public String getDescricao() {

		return descricao;
	}

	public void setDescricao(String descricao) {

		this.descricao = descricao;
	}

	public String getNome() {

		return nome;
	}

	public void setNome(String nome) {

		this.nome = nome;
	}

	public List<Municipio> getMunicipioRegiaoList() {

		return municipioRegiaoList;
	}

	public void setMunicipioRegiaoList(List<Municipio> municipioRegiaoList) {

		this.municipioRegiaoList = municipioRegiaoList;
	}

}
