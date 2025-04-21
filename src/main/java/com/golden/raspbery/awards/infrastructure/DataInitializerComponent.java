package com.golden.raspbery.awards.infrastructure;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.golden.raspbery.awards.services.MovieService;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializerComponent {

	private MovieService movieService;

	@PostConstruct
	public void init() {
		ClassPathResource classPathResource = new ClassPathResource("movielist.csv");

		if (!classPathResource.exists()) {
			return;
		}

		InputStream inputStream;
		try {
			inputStream = classPathResource.getInputStream();
		} catch (IOException e) {
			return;
		}

		this.movieService.registerMoviesFromCSVInputStream(inputStream);
	}

	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}
