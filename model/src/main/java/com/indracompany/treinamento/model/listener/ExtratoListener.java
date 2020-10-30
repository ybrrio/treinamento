package com.indracompany.treinamento.model.listener;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import com.indracompany.treinamento.model.entity.Extrato;

public class ExtratoListener {
	
	@PrePersist
	protected void onCreate(Extrato extrato) {
		LocalDateTime hoje = LocalDateTime.now();
		extrato.setDataHora(hoje);
	}

}
