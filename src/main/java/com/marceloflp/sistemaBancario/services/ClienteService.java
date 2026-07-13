package com.marceloflp.sistemaBancario.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marceloflp.sistemaBancario.entities.Cliente;
import com.marceloflp.sistemaBancario.repositories.ClienteRepository;
import com.marceloflp.sistemaBancario.services.exceptions.DatabaseException;
import com.marceloflp.sistemaBancario.services.exceptions.ResourceNotFoundException;

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
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return cliente;
	}
	
	public Cliente criarCliente(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Cliente atualizaCliente(Long id, Cliente clienteAtualizado) {
		try {
			Cliente cliente = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			updateCliente(cliente, clienteAtualizado);
			return repository.save(cliente);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possível atualizar o cliente devido a uma restrição de integridade.");
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
			
			Cliente cliente = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			repository.delete(cliente);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possível excluir o cliente devido a uma restrição de integridade.");
		}
	}
}
