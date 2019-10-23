package com.lfec.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

@MappedSuperclass
public class Entidade {

	@Id
	@Column(length = 36)
	public String id;
	
	@Version
	public int versao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}
	
	
	@PrePersist
	public void atualizaId() {
		if (id==null) {
			id = UUID.randomUUID().toString();
		}
	}
	
	public boolean isNew() {
		return id == null;
	}
	
	
}
