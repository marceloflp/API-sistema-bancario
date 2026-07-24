package com.marceloflp.sistemaBancario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marceloflp.sistemaBancario.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

}
