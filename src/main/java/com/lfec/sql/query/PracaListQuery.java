package com.lfec.sql.query;

import java.util.Map;

import com.lfec.sql.controller.Query;
import com.lfec.sql.controller.QueryReturn;
import com.lfec.sql.domain.Praca;

public class PracaListQuery extends Query<Praca> {
	
	public int cepIni;
	public int codMunicipioIni;
	public int codUfIni;
	public int codPaisIni;
	
	public int cepFim;
	public int codMunicipioFim;
	public int codUfFim;
	public int codPaisFim;
	
	public static String SUBQUERY = "select p.negociacao.id, min(p.order_index) as o_index  from Praca p "
			+ " left join p.areaOrigem.regiao.municipioRegiaoList as regIni"
			+ " left join p.areaDestino.regiao.municipioRegiaoList as regFim"
			+ " WHERE ((p.areaOrigem.tipoAreaGeografica='CEP' and :cepIni >= p.areaOrigem.cepInicio and :cepIni <= p.areaOrigem.cepTermino)"
			+" or (p.areaOrigem.tipoAreaGeografica='MUNICIPIO' and :codMunicipioIni = p.areaOrigem.codigoMun)"
			+" or (p.areaOrigem.tipoAreaGeografica='UF' and :codUfIni = p.areaOrigem.codigoUf)"
			+" or (p.areaOrigem.tipoAreaGeografica='PAIS' and :codPaisIni = p.areaOrigem.codigoPais)"
			+" or (p.areaOrigem.tipoAreaGeografica='REGIAO' and :codMunicipioIni = regIni.codigo))"
			+" and ((p.areaDestino.tipoAreaGeografica='CEP' and :cepFim >= p.areaDestino.cepInicio and :cepFim <= p.areaDestino.cepTermino)"
			+" or (p.areaDestino.tipoAreaGeografica='MUNICIPIO' and :codMunicipioFim = p.areaDestino.codigoMun)"
			+" or (p.areaDestino.tipoAreaGeografica='UF' and :codUfFim = p.areaDestino.codigoUf)"
			+" or (p.areaDestino.tipoAreaGeografica='PAIS' and :codPaisFim = p.areaDestino.codigoPais)"
			+" or (p.areaDestino.tipoAreaGeografica='REGIAO' and :codMunicipioFim = regFim.codigo))"
			+ " group by p.negociacao.id";
	
	public static String query = "select q.negociacao.id, q.id, q.order_index from Praca q where (q.negociacao.id, q.order_index) in ( "+ SUBQUERY+" )";
	
	
	public static String GROUP_BY = "";
	
	public static String ORDER_BY = "";

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
		mapa.put("cepIni",getCepIni());
		mapa.put("codMunicipioIni",getCodMunicipioIni());
		mapa.put("codUfIni",getCodUfIni());
		mapa.put("codPaisIni",getCodPaisIni());
		mapa.put("cepFim",getCepFim());
		mapa.put("codMunicipioFim",getCodMunicipioFim());
		mapa.put("codUfFim",getCodUfFim());
		mapa.put("codPaisFim",getCodPaisFim());
		
	}

	public int getCepIni() {
		return cepIni;
	}

	public void setCepIni(int cepIni) {
		this.cepIni = cepIni;
	}

	public int getCodMunicipioIni() {
		return codMunicipioIni;
	}

	public void setCodMunicipioIni(int codMunicipioIni) {
		this.codMunicipioIni = codMunicipioIni;
	}

	public int getCodUfIni() {
		return codUfIni;
	}

	public void setCodUfIni(int codUfIni) {
		this.codUfIni = codUfIni;
	}

	public int getCodPaisIni() {
		return codPaisIni;
	}

	public void setCofPaisIni(int cofPaisIni) {
		this.codPaisIni = cofPaisIni;
	}

	public int getCepFim() {
		return cepFim;
	}

	public void setCepFim(int cepFim) {
		this.cepFim = cepFim;
	}

	public int getCodMunicipioFim() {
		return codMunicipioFim;
	}

	public void setCodMunicipioFim(int codMunicipioFim) {
		this.codMunicipioFim = codMunicipioFim;
	}

	public int getCodUfFim() {
		return codUfFim;
	}

	public void setCodUfFim(int codUfFim) {
		this.codUfFim = codUfFim;
	}

	public int getCodPaisFim() {
		return codPaisFim;
	}

	public void setCofPaisFim(int cofPaisFim) {
		this.codPaisFim = cofPaisFim;
	}

}
