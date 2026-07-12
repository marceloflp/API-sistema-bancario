package com.marceloflp.sistemaBancario.services;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.marceloflp.sistemaBancario.entities.Cliente;
import com.marceloflp.sistemaBancario.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	// O TRATAMENTO DE EXCEÇÕES SERÁ FEITO DEPOIS

	private final ClienteRepository repository;

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public List<Cliente> buscarClientes(){
		return repository.findAll();
	}
	
	public Cliente buscarClientePorId(Long id) {
		Cliente cliente = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Erro genérico: cliente de id " + id + " não encontrado!"));
		
		return cliente;
	}
	
	public Cliente criarCliente(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Cliente atualizaCliente(Long id, Cliente clienteAtualizado) {
		try {
			Cliente cliente = repository.findById(id)
					.orElseThrow(() -> new RuntimeException("Erro genérico: cliente de id " + id + " não encontrado!"));
			
			updateCliente(cliente, clienteAtualizado);
			return repository.save(cliente);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void updateCliente(Cliente cliente, Cliente clienteAtualizado) {
		cliente.setCpf(clienteAtualizado.getCpf());
		cliente.setEmail(clienteAtualizado.getEmail());
		cliente.setNome(clienteAtualizado.getNome());
		cliente.setNumeroTelefone(clienteAtualizado.getNumeroTelefone());
		cliente.setSenha(clienteAtualizado.getSenha());
	}
	
	public void deletarCliente(Long id) {
		try {
			
			if(!repository.existsById(id)) throw new RuntimeException("Erro genérico: cliente de id " + id + " não encontrado!");
			Cliente cliente = repository.findById(id)
					.orElseThrow(() -> new RuntimeException("Erro genérico: cliente de id " + id + " não encontrado!"));
			
			repository.delete(cliente);
		}catch(EmptyResultDataAccessException e) {
			throw new RuntimeException("Erro genérico: cliente de id " + id + " não encontrado!");
		}
	}
}
