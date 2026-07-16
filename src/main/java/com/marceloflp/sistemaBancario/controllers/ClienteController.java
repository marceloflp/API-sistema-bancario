package com.marceloflp.sistemaBancario.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marceloflp.sistemaBancario.entities.Cliente;
import com.marceloflp.sistemaBancario.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}
	
	@GetMapping()
	public ResponseEntity<List<Cliente>> buscarClientes(){
		List<Cliente> clientes = service.buscarClientes();
		
		return ResponseEntity.ok().body(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id){
		Cliente cliente = service.buscarClientePorId(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@PostMapping()
	public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente body){
		Cliente cliente = service.criarCliente(body);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getIdCliente())
				.toUri();
		return ResponseEntity.created(uri).body(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente body){
		Cliente cliente = service.atualizaCliente(id, body);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
		service.deletarCliente(id);
		
		return ResponseEntity.noContent().build();
	}
}
