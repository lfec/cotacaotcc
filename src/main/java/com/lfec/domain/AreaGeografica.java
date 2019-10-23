package com.lfec.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Embeddable
public class AreaGeografica {

	public enum TipoAreaGeografica {
		MUNICIPIO, REGIAO, UF, PAIS, CEP
	}

	@Enumerated(EnumType.STRING)
	private TipoAreaGeografica tipoAreaGeografica;

	@Column(length = 7)
	private Integer codigoMun;
	
	@Column(length = 60)
	private String nomeMun;

	@ManyToOne(cascade = {CascadeType.MERGE})
	private Regiao regiao;

	@Column(length = 2)
	private Integer codigoUf;
	
	@Column(length = 2)
	private String siglaUf;

	@Column(length = 4)
	private Integer codigoPais;
	
	@Column(length = 5)
	private String nomePais;
	
	@Column(length = 8)
	private Integer cepInicio;
	
	@Column(length = 8)
	private Integer cepTermino;
	

	public AreaGeografica() {
		tipoAreaGeografica = TipoAreaGeografica.PAIS;
	}

	public TipoAreaGeografica getTipoAreaGeografica() {
		return tipoAreaGeografica;
	}

	public void setTipoAreaGeografica(TipoAreaGeografica tipoAreaGeografica) {
		this.tipoAreaGeografica = tipoAreaGeografica;
	}


	public Integer getCodigoMun() {
		return codigoMun;
	}

	public void setCodigoMun(Integer codigoMun) {
		this.codigoMun = codigoMun;
	}

	public String getNomeMun() {
		return nomeMun;
	}

	public void setNomeMun(String nomeMun) {
		this.nomeMun = nomeMun;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public Integer getCodigoUf() {
		return codigoUf;
	}

	public void setCodigoUf(Integer codigoUf) {
		this.codigoUf = codigoUf;
	}

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	public Integer getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(Integer codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String momePais) {
		this.nomePais = momePais;
	}

	public Integer getCepInicio() {
		return cepInicio;
	}

	public void setCepInicio(Integer cepInicio) {
		this.cepInicio = cepInicio;
	}

	public Integer getCepTermino() {
		return cepTermino;
	}

	public void setCepTermino(Integer cepTermino) {
		this.cepTermino = cepTermino;
	}

}
