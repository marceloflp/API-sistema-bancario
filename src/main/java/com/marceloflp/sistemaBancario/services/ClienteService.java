package com.marceloflp.sistemaBancario.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marceloflp.sistemaBancario.dtos.ClienteRequestDTO;
import com.marceloflp.sistemaBancario.dtos.ClienteResponseDTO;
import com.marceloflp.sistemaBancario.entities.Cliente;
import com.marceloflp.sistemaBancario.repositories.ClienteRepository;
import com.marceloflp.sistemaBancario.services.exceptions.DatabaseException;
import com.marceloflp.sistemaBancario.services.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public List<ClienteResponseDTO> buscarClientes(){
		List<Cliente> clientes = repository.findAll();
		return clientes.stream()
				.map(this::toDTO)
				.toList();
	}
	
	public ClienteResponseDTO buscarClientePorId(Long id) {
		Cliente cliente = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return toDTO(cliente);
	}
	
	public Cliente criarCliente(ClienteRequestDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setCpf(dto.cpf());
		cliente.setEmail(dto.email());
		cliente.setNome(dto.nome());
		cliente.setSenha(dto.senha());
		cliente.setNumeroTelefone(dto.numeroTelefone());
		return repository.save(cliente);
	}
	
	public ClienteResponseDTO atualizaCliente(Long id, Cliente clienteAtualizado) {
		try {
			Cliente cliente = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			updateCliente(cliente, clienteAtualizado);
			repository.save(cliente);
			return toDTO(cliente);
			
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
	
	public ClienteResponseDTO toDTO(Cliente cliente) {
		return new ClienteResponseDTO(cliente.getIdCliente(), cliente.getNome(), cliente.getEmail(), cliente.getNumeroTelefone());
	}
}
