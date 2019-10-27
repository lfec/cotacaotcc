package com.lfec.sql.controller;

import java.util.List;

import com.lfec.sql.domain.Entidade;

public class QueryReturn<T extends Entidade> {
	
	private List<T> listaRetorno;
	private List<Object[]> objectList;
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
	public List<Object[]> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<Object[]> objectList) {
		this.objectList = objectList;
	}
	
	

}
