package com.marceloflp.sistemaBancario.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marceloflp.sistemaBancario.dtos.ContaRequestDTO;
import com.marceloflp.sistemaBancario.dtos.ContaResponseDTO;
import com.marceloflp.sistemaBancario.entities.Conta;
import com.marceloflp.sistemaBancario.repositories.ContaRepository;
import com.marceloflp.sistemaBancario.services.exceptions.DatabaseException;
import com.marceloflp.sistemaBancario.services.exceptions.ResourceNotFoundException;

@Service
public class ContaService {

	private final ContaRepository repository;

	public ContaService(ContaRepository repository) {
		this.repository = repository;
	}
	
	public List<ContaResponseDTO> buscarContas(){
		List<Conta> contas = repository.findAll();
		return contas.stream()
				.map(this::toDTO)
				.toList();
	}
	
	public ContaResponseDTO buscarContaPorId(Long id) {
		Conta conta = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return toDTO(conta);
	}
	
	public ContaResponseDTO criarConta(ContaRequestDTO dto) {
		
		Conta conta = new Conta();
		conta.setAgencia(dto.agencia());
		conta.setNumeroConta(dto.numeroConta());
		conta.setStatus(dto.status());
		conta.setTipo(conta.getTipo());
		
		repository.save(conta);
		return toDTO(conta);
	}
	
	public ContaResponseDTO atualizaConta(Long id, ContaRequestDTO dto) {
		try {
			Conta conta = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			updateConta(conta, dto);
			repository.save(conta);
			return toDTO(conta);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possível atualizar a conta devido a uma restrição de integridade.");
		}
	}

	private void updateConta(Conta conta, ContaRequestDTO dto) {
		conta.setAgencia(dto.agencia());
		conta.setNumeroConta(dto.numeroConta());
		conta.setStatus(dto.status());
		conta.setTipo(dto.tipo());
	}
	
	public void deletarConta(Long id) {
		try {
			
			Conta conta = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			repository.delete(conta);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possível excluir a conta devido a uma restrição de integridade.");
		}
	}
	
	public ContaResponseDTO toDTO(Conta conta) {
		return new ContaResponseDTO(conta.getIdConta(), conta.getNumeroConta(), conta.getAgencia(), 
				conta.getTipo(), conta.getStatus(), conta.getDataCriacao());
	}
}
