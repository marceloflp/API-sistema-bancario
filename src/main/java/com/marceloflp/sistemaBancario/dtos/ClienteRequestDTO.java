package com.marceloflp.sistemaBancario.dtos;

public record ClienteRequestDTO(Long idCliente, String nome, String cpf, String email, String senha, String numeroTelefone) {

}
