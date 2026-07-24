package com.marceloflp.sistemaBancario.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
	
	public List<Conta> buscarContas(){
		return repository.findAll();
	}
	
	public Conta buscarContaPorId(Long id) {
		Conta conta = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return conta;
	}
	
	public Conta criarConta(Conta conta) {
		return repository.save(conta);
	}
	
	public Conta atualizaConta(Long id, Conta contaAtualizado) {
		try {
			Conta conta = repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id));
			
			updateConta(conta, contaAtualizado);
			return repository.save(conta);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possível atualizar a conta devido a uma restrição de integridade.");
		}
	}

	private void updateConta(Conta conta, Conta contaAtualizado) {
		conta.setAgencia(contaAtualizado.getAgencia());
		conta.setDataCriacao(contaAtualizado.getDataCriacao());
		conta.setNumeroConta(conta.getNumeroConta());
		conta.setStatus(conta.getStatus());
		conta.setTipo(conta.getTipo());
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
}
