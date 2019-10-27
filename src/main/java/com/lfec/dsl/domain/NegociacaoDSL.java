package com.lfec.dsl.domain;

import java.util.List;

public class NegociacaoDSL {
	
	
	private String idnegociacao;
	
	private List<PracaDSL> pracaList;

	public NegociacaoDSL(String idnegociacao) {
		this.idnegociacao = idnegociacao;
	}

	public String getIdnegociacao() {
		return idnegociacao;
	}

	public void setIdnegociacao(String idnegociacao) {
		this.idnegociacao = idnegociacao;
	}

	public List<PracaDSL> getPracaList() {
		return pracaList;
	}

	public void setPracaList(List<PracaDSL> pracaList) {
		this.pracaList = pracaList;
	}

	
	
}
