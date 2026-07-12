package com.marceloflp.sistemaBancario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marceloflp.sistemaBancario.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
