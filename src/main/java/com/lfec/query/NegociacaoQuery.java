package com.lfec.query;

import java.util.Map;

import com.lfec.controller.Query;
import com.lfec.controller.QueryReturn;
import com.lfec.domain.Negociacao;

public class NegociacaoQuery extends Query<Negociacao> {

	@Override
	public QueryReturn<Negociacao> getQueryReturn() {
		return new QueryReturn<Negociacao>();
	}

	@Override
	public Class<Negociacao> getEntidadeClass() {
		return Negociacao.class;
	}

	@Override
	public String getSelectQuery() {
		return "Select n from Negociacao n";
	}

	@Override
	protected void populaMapa(Map<String, Object> mapa) {
		// TODO Auto-generated method stub
		
	}

}
