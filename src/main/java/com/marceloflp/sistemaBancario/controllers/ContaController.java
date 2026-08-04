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

import com.marceloflp.sistemaBancario.dtos.ContaRequestDTO;
import com.marceloflp.sistemaBancario.dtos.ContaResponseDTO;
import com.marceloflp.sistemaBancario.services.ContaService;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
	
	private final ContaService service;

	public ContaController(ContaService service) {
		this.service = service;
	}
	
	@GetMapping()
	public ResponseEntity<List<ContaResponseDTO>> buscarContas(){
		List<ContaResponseDTO> contas = service.buscarContas();
		
		return ResponseEntity.ok().body(contas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContaResponseDTO> buscarConta(@PathVariable Long id){
		ContaResponseDTO conta = service.buscarContaPorId(id);
		
		return ResponseEntity.ok().body(conta);
	}
	
	@PostMapping()
	public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO body){
		ContaResponseDTO conta = service.criarConta(body);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(conta.idConta())
				.toUri();
		return ResponseEntity.created(uri).body(conta);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContaResponseDTO> atualizarConta(@PathVariable Long id, @RequestBody ContaRequestDTO body){
		ContaResponseDTO conta = service.atualizaConta(id, body);
		
		return ResponseEntity.ok().body(conta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarConta(@PathVariable Long id){
		service.deletarConta(id);
		
		return ResponseEntity.noContent().build();
	}
}
