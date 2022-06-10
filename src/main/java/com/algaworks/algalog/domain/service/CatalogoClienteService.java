package com.algaworks.algalog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegotiationException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

@Service
public class CatalogoClienteService { //class to manage all our negotiation service rules
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente buscar(Long clienteId) { //return client or throws a NegotiationException
		return clienteRepository.findById(clienteId)
			.orElseThrow(() -> new NegotiationException(
					"Cliente de (id = " + clienteId  
					+ ") não encontrado!"));
		
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteOwner -> !clienteOwner.equals(cliente));
		
		if(emailEmUso) { //if email belongs someone
			throw new NegotiationException(
				"Outro usuário já possui o e-mail: " + cliente.getEmail());
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	

}
