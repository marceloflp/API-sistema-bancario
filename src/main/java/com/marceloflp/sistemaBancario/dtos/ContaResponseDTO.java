package com.marceloflp.sistemaBancario.dtos;

import java.time.LocalDateTime;

public record ContaResponseDTO(Long idConta, String numeroConta, String conta, String tipo, String status, LocalDateTime dataCriacao) {

}
