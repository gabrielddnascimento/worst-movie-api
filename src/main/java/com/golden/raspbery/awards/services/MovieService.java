package com.golden.raspbery.awards.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.golden.raspbery.awards.dtos.MovieDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.models.Movie;
import com.golden.raspbery.awards.models.Producer;
import com.golden.raspbery.awards.models.Studio;
import com.golden.raspbery.awards.repositories.MovieRepository;
import com.golden.raspbery.awards.repositories.ProducerRepository;
import com.golden.raspbery.awards.repositories.StudioRepository;

@Service
public class MovieService {

	private MovieRepository movieRepository;
	private ProducerRepository producerRepository;
	private StudioRepository studioRepository;

	public List<MovieDTO> listMovies() {
		Iterable<Movie> movieIterable = this.movieRepository.findAll();

		List<MovieDTO> movieDTOList = new ArrayList<>();
		movieIterable.forEach((movie) -> {
			movieDTOList.add(new MovieDTO(movie));
		});

		return movieDTOList;
	}

	public MovieDTO findMovieById(Long id) throws BusinessException {
		Optional<Movie> movieOptional = this.movieRepository.findById(id);

		if (movieOptional.isEmpty()) {
			throw ExceptionHelper.movieNotFound(id);
		}

		return new MovieDTO(movieOptional.get());
	}

	public MovieDTO registerMovie(MovieDTO movieDTO) throws BusinessException {
		if (movieDTO.getIsWinner()) {
			List<Movie> winnerMovieList = this.movieRepository.listMoviesByYearAndIsWinner(movieDTO.getYear(), true);
			if (!CollectionUtils.isEmpty(winnerMovieList)) {
				Movie winnerMovie = winnerMovieList.get(0);
				throw ExceptionHelper.yearAlreadyHasWinnerMovie(movieDTO.getYear(), winnerMovie.getTitle());
			}
		}

		Movie existentMovie = this.movieRepository.findMovieByYearAndTitle(movieDTO.getYear(), movieDTO.getTitle());

		if (existentMovie != null) {
			throw ExceptionHelper.movieAlreadyExistent(movieDTO.getTitle());
		}

		Movie movie = new Movie();
		movie.setTitle(movieDTO.getTitle());
		movie.setYear(movieDTO.getYear());
		movie.setIsWinner(movieDTO.getIsWinner());

		String filmProducersString = movieDTO.getFilmProducers().replace(", ", ",").replace(" and ", ",");
		String filmStudiosString = movieDTO.getFilmStudios().replace(", ", ",");

		List<String> producersNameList = Arrays.asList(filmProducersString.split(","));
		List<String> studiosNameList = Arrays.asList(filmStudiosString.split(","));

		Set<Producer> producersSet = this.producerRepository.findProducerByNameList(producersNameList);
		Set<Studio> studiosSet = this.studioRepository.findStudioByNameList(studiosNameList);

		if (producersSet.size() != producersNameList.size()) {
			producersNameList.forEach((producerName) -> {
				AtomicBoolean isNewProducer = new AtomicBoolean(true);
				producersSet.forEach((producer) -> {
					if (producer.getName().equals(producerName)) {
						isNewProducer.set(false);
					}
				});

				if (isNewProducer.get()) {
					Producer producer = new Producer();
					producer.setName(producerName);
					producersSet.add(producer);
				}
			});
		}

		if (studiosSet.size() != producersNameList.size()) {
			studiosNameList.forEach((studioName) -> {
				AtomicBoolean isNewStudio = new AtomicBoolean(true);
				studiosSet.forEach((studio) -> {
					if (studio.getName().equals(studioName)) {
						isNewStudio.set(false);
					}
				});

				if (isNewStudio.get()) {
					Studio studio = new Studio();
					studio.setName(studioName);
					studiosSet.add(studio);
				}
			});
		}

		movie.setProducersSet(producersSet);
		movie.setStudiosSet(studiosSet);

		movie = this.movieRepository.save(movie);

		return new MovieDTO(movie);
	}

	public MovieDTO editMovie(MovieDTO movieDTO) throws BusinessException {
		Optional<Movie> movieOptional = this.movieRepository.findById(movieDTO.getId());

		if (movieOptional.isEmpty()) {
			throw ExceptionHelper.movieNotFound(movieDTO.getId());
		}

		Movie movie = movieOptional.get();
		if (movieDTO.getIsWinner()) {
			List<Movie> winnerMovieList = this.movieRepository.listMoviesByYearAndIsWinner(movieDTO.getYear(), true);
			if (!CollectionUtils.isEmpty(winnerMovieList)) {
				Movie winnerMovie = winnerMovieList.get(0);
				throw ExceptionHelper.yearAlreadyHasWinnerMovie(movieDTO.getYear(), winnerMovie.getTitle());
			}
		}

		movie.setTitle(movieDTO.getTitle());
		movie.setYear(movieDTO.getYear());
		movie.setIsWinner(movieDTO.getIsWinner());

		String filmProducersString = movieDTO.getFilmProducers().replace(", ", ",").replace(" and ", ",");
		String filmStudiosString = movieDTO.getFilmStudios().replace(", ", ",");

		List<String> producersNameList = Arrays.asList(filmProducersString.split(","));
		List<String> studiosNameList = Arrays.asList(filmStudiosString.split(","));

		Set<Producer> producersSet = this.producerRepository.findProducerByNameList(producersNameList);
		Set<Studio> studiosSet = this.studioRepository.findStudioByNameList(studiosNameList);

		if (producersSet.size() != producersNameList.size()) {
			producersNameList.forEach((producerName) -> {
				AtomicBoolean isNewProducer = new AtomicBoolean(true);
				producersSet.forEach((producer) -> {
					if (producer.getName().equals(producerName)) {
						isNewProducer.set(false);
					}
				});

				if (isNewProducer.get()) {
					Producer producer = new Producer();
					producer.setName(producerName);
					producersSet.add(producer);
				}
			});
		}

		if (studiosSet.size() != producersNameList.size()) {
			studiosNameList.forEach((studioName) -> {
				AtomicBoolean isNewStudio = new AtomicBoolean(true);
				studiosSet.forEach((studio) -> {
					if (studio.getName().equals(studioName)) {
						isNewStudio.set(false);
					}
				});

				if (isNewStudio.get()) {
					Studio studio = new Studio();
					studio.setName(studioName);
					studiosSet.add(studio);
				}
			});
		}

		movie.setProducersSet(producersSet);
		movie.setStudiosSet(studiosSet);

		movie = this.movieRepository.save(movie);

		return new MovieDTO(movie);
	}

	public void deleteMovie(Long id) {
		this.movieRepository.deleteById(id);
	}

	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Autowired
	public void setProducerRepository(ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}

	@Autowired
	public void setStudioRepository(StudioRepository studioRepository) {
		this.studioRepository = studioRepository;
	}
}
