package com.marceloflp.sistemaBancario.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public ResourceNotFoundException(Object id) {
		super("Recurso de ID " + id + " não encontrado!");
	}
	
}
