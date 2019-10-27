package com.lfec.dsl.domain;

import java.util.List;

import com.lfec.domain.Endereco;

public class AreaDSL {

	public enum TipoAreaGeograficaDSL {
		MUNICIPIO("M"), REGIAO("R"), UF("U"), PAIS("P"), CEP("C");
		
		private String codigo;
		private TipoAreaGeograficaDSL(String cod) {
			this.codigo = cod;
		}
		
		public static TipoAreaGeograficaDSL fromValue(String cod) {
			TipoAreaGeograficaDSL ret = null;

			TipoAreaGeograficaDSL[] list = TipoAreaGeograficaDSL.values();
			
			for (TipoAreaGeograficaDSL tipoAreaGeograficaDSL : list) {
				if (tipoAreaGeograficaDSL.getCodigo().equalsIgnoreCase(cod)) {
					ret = tipoAreaGeograficaDSL;
					break;
				}
			}
			
			return ret;
		}

		public String getCodigo() {
			return codigo;
		}
	}
	
	private TipoAreaGeograficaDSL tipoArea;
	
	private Integer cepInicio;
	private Integer cepTermino;
	private Integer codMunicipio;
	private Integer codUf;
	private Integer codPais;
	private List<Integer> regiaoList;
	
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
	public Integer getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(Integer codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public Integer getCodUf() {
		return codUf;
	}
	public void setCodUf(Integer codUf) {
		this.codUf = codUf;
	}
	public Integer getCodPais() {
		return codPais;
	}
	public void setCodPais(Integer codPais) {
		this.codPais = codPais;
	}
	public List<Integer> getRegiaoList() {
		return regiaoList;
	}
	public void setRegiaoList(List<Integer> regiaoList) {
		this.regiaoList = regiaoList;
	}
	public TipoAreaGeograficaDSL getTipoArea() {
		return tipoArea;
	}
	public void setTipoArea(TipoAreaGeograficaDSL tipoArea) {
		this.tipoArea = tipoArea;
	}
	
	public boolean atende(Endereco endereco) {
		boolean ret = false;
		
		switch (tipoArea) {
		case CEP:
			ret = cepInicio<=endereco.getCep() && cepTermino>=endereco.getCep();
			break;
		case MUNICIPIO:
			ret = codMunicipio.equals(endereco.getCodigoMun());
			break;
		case UF:
			ret = codUf.equals(endereco.getCodigoUf());
			break;
		case REGIAO:
			
			for (Integer codMunicipio : regiaoList) {
				if (codMunicipio.equals(endereco.getCodigoMun())) {
					ret = true;
					break;
				}
			}
			
			
			break;
		case PAIS:
			ret = codPais.equals(endereco.getCodigoPais());
			break;

		default:
			break;
		}
		
		return ret;
	}
	
}
