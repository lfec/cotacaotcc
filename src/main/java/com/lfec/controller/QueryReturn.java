package com.lfec.controller;

import java.util.List;

import com.lfec.domain.Entidade;

public class QueryReturn<T extends Entidade> {
	
	private List<T> listaRetorno;
	private int total;
	public List<T> getListaRetorno() {
		return listaRetorno;
	}
	public void setListaRetorno(List<T> listaRetorno) {
		this.listaRetorno = listaRetorno;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
