package com.lfec.domain;

public class PracaWrapper {
	
	private String negociacaoId;
	private String pracaId;
	private int orderIndex;
	
	public PracaWrapper(String negociacaoId, String pracaId, int orderIndex) {
		this.negociacaoId = negociacaoId;
		this.pracaId = pracaId;
		this.orderIndex = orderIndex;
	}
	
	public String getNegociacaoId() {
		return negociacaoId;
	}
	public void setNegociacaoId(String negociacaoId) {
		this.negociacaoId = negociacaoId;
	}
	public String getPracaId() {
		return pracaId;
	}
	public void setPracaId(String pracaId) {
		this.pracaId = pracaId;
	}
	public int getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	
	

}
