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
		return new BusinessException("O estúdio de ID %d não foi encontrado.", id);
	}

	public static BusinessException studioNotSent() {
		return new BusinessException("O estúdio não foi enviado na requisição.");
	}

	public static BusinessException studioNameSentNotExistent() {
		return new BusinessException("O estúdio enviado não possui um nome.");
	}

	public static BusinessException studioAlreadyExistent() {
		return new BusinessException("O estúdio enviado já existe.");
	}

	public static BusinessException movieNotFound(Long id) {
		return new BusinessException("O filme de ID %d não foi encontrado.", id);
	}

	public static BusinessException movieAlreadyExistent(String title) {
		return new BusinessException("O filme \"%s\" já existe.", title);
	}

	public static BusinessException movieNotSent() {
		return new BusinessException("O produtor não foi enviado na requisição.");
	}

	public static BusinessException yearAlreadyHasWinnerMovie(int year, String winnerMovieTitle) {
		return new BusinessException("O ano %d já possui um filme vencedor: %s", year, winnerMovieTitle);
	}
}
