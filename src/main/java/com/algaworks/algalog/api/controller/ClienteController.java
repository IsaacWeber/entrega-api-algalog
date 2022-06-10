package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

@RestController
@RequestMapping("/clientes") //map class as '/clientes' endpoint
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(
			@Valid @RequestBody Cliente cliente) {
//		return clienteRepository.save(cliente);
		return catalogoClienteService.salvar(cliente); //passing by the service class where cliente will be validated
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		return clienteRepository.findById(clienteId)
//				.map(opCliente -> ResponseEntity.ok(opCliente)) 
				.map(ResponseEntity::ok) 
				.orElse(ResponseEntity.notFound().build());
//		Optional<Cliente> opCliente = clienteRepository.findById(clienteId); //pega o cliente com um container optional
//	
//		if(opCliente.isPresent()) {
//			return ResponseEntity.ok(opCliente.get()); //retorna status 200 ok e o corpo do cliente
//		}
//		
//		return ResponseEntity.notFound().build(); //retorna um valor com 404 not found
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(
			@PathVariable Long clienteId, 
			@Valid @RequestBody Cliente cliente) { 
		
		if(clienteRepository.existsById(clienteId)) { //if client id exists
			cliente.setId(clienteId); //put this id on client
			catalogoClienteService.salvar(cliente); //update client
			return ResponseEntity.ok(cliente); //return 200 ok http status and client
		}
		
		return ResponseEntity.notFound().build(); //return 404 not found http status
		
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> excluir(@PathVariable Long clienteId) { //delete method
		
		if(clienteRepository.existsById(clienteId)) { //if client exists
			catalogoClienteService.excluir(clienteId); //delete client by id by service class
			return ResponseEntity.noContent().build(); //return a 204 no content http status 
		}
		
		return ResponseEntity.notFound().build(); //if client does not exist return 404 not foud http status
	}
	//comment
		
}
