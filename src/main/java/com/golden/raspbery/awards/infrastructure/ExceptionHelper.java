package com.golden.raspbery.awards.infrastructure;

public class ExceptionHelper {

	public static BusinessException producerNotFound(Long id) {
		return new BusinessException("O produtor de ID %d não foi encontrado.", id);
	}

	public static BusinessException producerNotSent() {
		return new BusinessException("O produtor não foi enviado na requisição.");
	}

	public static BusinessException producerNameSentNotExistent() {
		return new BusinessException("O produtor enviado não possui um nome.");
	}

	public static BusinessException producerAlreadyExistent() {
		return new BusinessException("O produtor enviado já existe.");
	}

	public static BusinessException studioNotFound(Long id) {
		return new BusinessException("O produtor de ID %d não foi encontrado.", id);
	}

	public static BusinessException studioNotSent() {
		return new BusinessException("O produtor não foi enviado na requisição.");
	}

	public static BusinessException studioNameSentNotExistent() {
		return new BusinessException("O produtor enviado não possui um nome.");
	}

	public static BusinessException studioAlreadyExistent() {
		return new BusinessException("O produtor enviado já existe.");
	}
}
