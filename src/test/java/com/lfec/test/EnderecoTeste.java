package com.lfec.test;

import com.lfec.sql.domain.Regiao;

public class EnderecoTeste {

	private Integer codigoMun;

	private Integer codigoUf;

	private Integer codigoPais;

	private Integer cep;

	private Regiao regiao;

	public EnderecoTeste(Integer cep, Integer codigoMun, Integer codigoUf, Integer codigoPais) {
		this.cep = cep;
		this.codigoMun = codigoMun;
		this.codigoUf = codigoUf;
		this.codigoPais = codigoPais;
	}

	public Integer getCodigoMun() {
		return codigoMun;
	}

	public void setCodigoMun(Integer codigoMun) {
		this.codigoMun = codigoMun;
	}

	public Integer getCodigoUf() {
		return codigoUf;
	}

	public void setCodigoUf(Integer codigoUf) {
		this.codigoUf = codigoUf;
	}

	public Integer getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(Integer codigoPais) {
		this.codigoPais = codigoPais;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

}
