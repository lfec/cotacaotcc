package com.lfec.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "cotacao_Negociacao")
public class Negociacao extends Entidade{

	private String nome;
	private String empresaNome;
	@OneToMany(mappedBy = "negociacao", cascade = CascadeType.ALL)
	@OrderColumn(name = "order_index")
	private List<Praca> pracaList;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmpresaNome() {
		return empresaNome;
	}
	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}
	public List<Praca> getPracaList() {
		return pracaList;
	}
	public void setPracaList(List<Praca> pracaList) {
		this.pracaList = pracaList;
	}

	
	
}
