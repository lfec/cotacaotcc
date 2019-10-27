package com.lfec.sql.controller;

import java.util.HashMap;
import java.util.Map;

import com.lfec.sql.domain.Entidade;

public abstract class Query <T extends Entidade> {
	
	public int offSet;
	public int limit;
	
	public Query() {
		offSet=0;
		limit =0;
	}
	
	public abstract QueryReturn<T> getQueryReturn();

	public abstract Class<T> getEntidadeClass();
	
	public abstract String getSelectQuery();

	protected Map<String, Object> getParamMap(){
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		populaMapa(mapa);
		
		return mapa;
	}

	protected abstract void populaMapa(Map<String, Object> mapa);

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getOffSet() {
		return offSet;
	}

	public int getLimit() {
		return limit;
	}
}
