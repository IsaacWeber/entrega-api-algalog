package com.algaworks.algalog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegotiationException;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.repository.EntregaRepository;

@Service
public class RegistroOcorrenciaService {
	
	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private BuscaEntregaService buscaEntregaService;
	
	@Transactional
	public Ocorrencia registrar(Long entregaId, String descricao) {
		var entrega = buscaEntregaService.buscar(entregaId);
		
		return entrega.addOcorrencia(descricao);
		
	}
	
}
