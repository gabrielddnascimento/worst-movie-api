package com.golden.raspbery.awards.services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspbery.awards.dtos.MovieDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.models.Movie;
import com.golden.raspbery.awards.models.Producer;
import com.golden.raspbery.awards.models.Studio;
import com.golden.raspbery.awards.repositories.MovieRepository;
import com.golden.raspbery.awards.repositories.ProducerRepository;
import com.golden.raspbery.awards.repositories.StudioRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

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
		Movie movie = new Movie();
		movie.setTitle(movieDTO.getTitle());
		movie.setYear(movieDTO.getYear());
		movie.setIsWinner(movieDTO.getIsWinner());

		String filmProducersString = movieDTO.getMovieProducers().replace(", ", ",").replace(" and ", ",");
		String filmStudiosString = movieDTO.getMovieStudios().replace(", ", ",");

		List<String> producersNameList = Arrays.asList(filmProducersString.split(","));
		List<String> studiosNameList = Arrays.asList(filmStudiosString.split(","));

		Set<Producer> producersSet = new HashSet<>();
		for (String name : producersNameList) {
			Producer producer = producerRepository.findProducerByName(name).orElseGet(() -> {
				Producer newProducer = new Producer();
				newProducer.setName(name);

				return producerRepository.save(newProducer);
			});

			producersSet.add(producer);
		}

		Set<Studio> studiosSet = new HashSet<>();
		for (String name : studiosNameList) {
			Studio studio = studioRepository.findStudioByName(name).orElseGet(() -> {
				Studio newStudio = new Studio();
				newStudio.setName(name);

				return studioRepository.save(newStudio);
			});

			studiosSet.add(studio);
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
		movie.setTitle(movieDTO.getTitle());
		movie.setYear(movieDTO.getYear());
		movie.setIsWinner(movieDTO.getIsWinner());

		String filmProducersString = movieDTO.getMovieProducers().replace(", ", ",").replace(" and ", ",");
		String filmStudiosString = movieDTO.getMovieStudios().replace(", ", ",");

		List<String> producersNameList = Arrays.asList(filmProducersString.split(","));
		List<String> studiosNameList = Arrays.asList(filmStudiosString.split(","));

		Set<Producer> producersSet = new HashSet<>();
		for (String name : producersNameList) {
			Producer producer = producerRepository.findProducerByName(name).orElseGet(() -> {
				Producer newProducer = new Producer();
				newProducer.setName(name);

				return producerRepository.save(newProducer);
			});

			producersSet.add(producer);
		}

		Set<Studio> studiosSet = new HashSet<>();
		for (String name : studiosNameList) {
			Studio studio = studioRepository.findStudioByName(name).orElseGet(() -> {
				Studio newStudio = new Studio();
				newStudio.setName(name);

				return studioRepository.save(newStudio);
			});

			studiosSet.add(studio);
		}

		movie.setProducersSet(producersSet);
		movie.setStudiosSet(studiosSet);

		movie = this.movieRepository.save(movie);

		return new MovieDTO(movie);
	}

	public List<MovieDTO> registerMoviesFromCSVInputStream(InputStream inputStream) {
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		CSVReader reader = new CSVReaderBuilder(inputStreamReader)
				.withCSVParser(parser)
				.build();

		CsvToBean<MovieDTO> csvToBean = new CsvToBeanBuilder<MovieDTO>(reader)
				.withIgnoreLeadingWhiteSpace(true)
				.withType(MovieDTO.class)
				.build();

		List<MovieDTO> movieDTOsList = csvToBean.parse();

		for(MovieDTO movieDTO : movieDTOsList) {
			try {
				MovieDTO returnMovieDTO = this.registerMovie(movieDTO);
				movieDTO.setId(returnMovieDTO.getId());
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}

		return movieDTOsList;
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
