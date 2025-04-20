package com.golden.raspbery.awards.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.golden.raspbery.awards.dtos.MovieDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.services.MovieService;

@Controller
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;

	@GetMapping
	public ResponseEntity<List<MovieDTO>> listMovies() {
		List<MovieDTO> movieDTOsList = this.movieService.listMovies();

		return ResponseEntity.ok(movieDTOsList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieDTO> findMovieById(@PathVariable Long id) throws BusinessException {
		MovieDTO movieDTO = this.movieService.findMovieById(id);

		return ResponseEntity.ok(movieDTO);
	}

	@PostMapping
	public ResponseEntity<MovieDTO> registerMovie(@RequestBody MovieDTO movieDTO) throws BusinessException {
		if (movieDTO == null) {
			throw ExceptionHelper.movieNotSent();
		}

		movieDTO = this.movieService.registerMovie(movieDTO);

		return ResponseEntity.ok(movieDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MovieDTO> editMovie(@RequestBody MovieDTO movieDTO, @PathVariable Long id) throws BusinessException {
		movieDTO.setId(id);
		movieDTO = this.movieService.editMovie(movieDTO);

		return ResponseEntity.ok(movieDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteMovie(@PathVariable Long id) {
		this.movieService.deleteMovie(id);

		return ResponseEntity.ok(Boolean.TRUE);
	}

	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}
