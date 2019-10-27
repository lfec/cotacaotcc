package com.lfec.sql.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lfec.domain.Endereco;

@Entity
@Table(name = "cotacao_Praca", indexes = {@Index(columnList = "order_index", name = "orderIndex"),@Index(columnList = "negociacao_id, order_index", name = "negociacaoOrderIndex")})
public class Praca extends Entidade {

	@ManyToOne(fetch = FetchType.EAGER)
	private Negociacao negociacao;

	@AttributeOverrides({
			@AttributeOverride(name = "tipoAreaGeografica", column = @Column(name = "tipoAreaGeograficaOrigem")),
			@AttributeOverride(name = "codigoMun", column = @Column(name = "codigoMunicipioOrigem")),
			@AttributeOverride(name = "nomeMun", column = @Column(name = "nomeMunicipioOrigem")),
			@AttributeOverride(name = "codigoUf", column = @Column(name = "codigoUfOrigem")),
			@AttributeOverride(name = "siglaUf", column = @Column(name = "siglaUfOrigem")),
			@AttributeOverride(name = "codigoPais", column = @Column(name = "codigoPaisOrigem")),
			@AttributeOverride(name = "nomePais", column = @Column(name = "nomePaisOrigem")),
			@AttributeOverride(name = "cepInicio", column = @Column(name = "cepOrigemInicio")),
			@AttributeOverride(name = "cepTermino", column = @Column(name = "cepOrigemTermino"))})
	@AssociationOverrides({
			@AssociationOverride(name = "regiao", joinColumns = { @JoinColumn(name = "regiaoOrigem_id") }) })
	private AreaGeografica areaOrigem;

	@AttributeOverrides({
			@AttributeOverride(name = "tipoAreaGeografica", column = @Column(name = "tipoAreaGeograficaDestino")),
			@AttributeOverride(name = "codigoMun", column = @Column(name = "codigoMunicipioDestino")),
			@AttributeOverride(name = "nomeMun", column = @Column(name = "nomeMunicipioDestino")),
			@AttributeOverride(name = "codigoUf", column = @Column(name = "codigoUfDestino")),
			@AttributeOverride(name = "siglaUf", column = @Column(name = "siglaUfDestino")),
			@AttributeOverride(name = "codigoPais", column = @Column(name = "codigoPaisDestino")),
			@AttributeOverride(name = "nomePais", column = @Column(name = "nomePaisDestino")),
			@AttributeOverride(name = "cepInicio", column = @Column(name = "cepDestinoInicio")),
			@AttributeOverride(name = "cepTermino", column = @Column(name = "cepDestinoTermino")) })
	@AssociationOverrides({
			@AssociationOverride(name = "regiao", joinColumns = { @JoinColumn(name = "regiaoDestino_id") }) })
	private AreaGeografica areaDestino;
	
	private int order_index;

	public Negociacao getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(Negociacao negociacao) {
		this.negociacao = negociacao;
	}

	public AreaGeografica getAreaOrigem() {
		return areaOrigem;
	}

	public void setAreaOrigem(AreaGeografica areaOrigem) {
		this.areaOrigem = areaOrigem;
	}

	public AreaGeografica getAreaDestino() {
		return areaDestino;
	}

	public void setAreaDestino(AreaGeografica areaDestino) {
		this.areaDestino = areaDestino;
	}

	public int getOrder_index() {
		return order_index;
	}

	public void setOrder_index(int order_index) {
		this.order_index = order_index;
	}
	
	public boolean atende(Endereco origem, Endereco destino) {
		return areaOrigem.atende(origem) && areaDestino.atende(destino);
	}

}
