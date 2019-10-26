package com.lfec.query;

import java.util.Map;

import com.lfec.controller.Query;
import com.lfec.controller.QueryReturn;
import com.lfec.domain.Praca;

public class AllPracaQuery extends Query<Praca> {
	
	
	public static String query = "select q from Praca q";
	
	
	@Override
	public QueryReturn<Praca> getQueryReturn() {
		return new QueryReturn<Praca>();
	}

	@Override
	public Class<Praca> getEntidadeClass() {
		return Praca.class;
	}

	@Override
	public String getSelectQuery() {
		return query;
	}

	@Override
	protected void populaMapa(Map<String, Object> mapa) {
		
	}


}
