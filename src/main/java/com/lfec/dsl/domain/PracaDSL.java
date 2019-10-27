package com.lfec.dsl.domain;

import com.lfec.domain.Endereco;

public class PracaDSL {

	private String idPraca;
	private String idNegociacao;
	private int orderIndex;
	private AreaDSL origem;
	private AreaDSL destino;

	public PracaDSL(String idPraca,String idNegociacao, String order) {
		this.idPraca = idPraca;
		this.idNegociacao = idNegociacao;
		this.orderIndex = Integer.parseInt(order);
	}

	public String getIdPraca() {
		return idPraca;
	}

	public void setIdPraca(String idPraca) {
		this.idPraca = idPraca;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public AreaDSL getOrigem() {
		return origem;
	}

	public void setOrigem(AreaDSL origem) {
		this.origem = origem;
	}

	public AreaDSL getDestino() {
		return destino;
	}

	public void setDestino(AreaDSL destino) {
		this.destino = destino;
	}
	
	public boolean atende(Endereco origem, Endereco destino) {
		return this.origem.atende(origem) && this.destino.atende(destino);
	}

	public String getIdNegociacao() {
		return idNegociacao;
	}

	public void setIdNegociacao(String idNegociacao) {
		this.idNegociacao = idNegociacao;
	}

}
