package com.golden.raspbery.awards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.golden.raspbery.awards.dtos.MovieDTO;

@TestMethodOrder(value = OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieIT extends TestBase {

	private static final String MOVIES_ENDPOINT = "/movies";

	private static MovieDTO movieDTO;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@Order(1)
	public void createMovieTest() {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setTitle("Nome: O Filme");
		movieDTO.setYear(1900);
		movieDTO.setIsWinner(true);
		movieDTO.setFilmStudios("Alpha Video, Cinemark");
		movieDTO.setFilmProducers("ProdutorUm, ProdutorDois");

		ResponseEntity<MovieDTO> responseEntity = this.testRestTemplate.postForEntity(this.getEndpoint(), movieDTO, MovieDTO.class);
		MovieIT.movieDTO = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MovieIT.movieDTO, movieDTO);
	}

	@Test
	@Order(2)
	public void createSecondWinnerOnTheSameYearTest() {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setTitle("Nome: O Curta");
		movieDTO.setYear(1900);
		movieDTO.setIsWinner(true);
		movieDTO.setFilmStudios("Alpha Video, Cinemark");
		movieDTO.setFilmProducers("ProdutorUm, ProdutorDois");

		ResponseEntity<MovieDTO> responseEntity = this.testRestTemplate.postForEntity(this.getEndpoint(), movieDTO, MovieDTO.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotEquals(movieDTO, responseEntity.getBody());
	}

	@Test
	@Order(3)
	public void listMoviesTest() {
		ResponseEntity<MovieDTO[]> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(), MovieDTO[].class);
		MovieDTO[] movieDTOsArray = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assert movieDTOsArray.length > 0;
	}

	@Test
	@Order(4)
	public void findMovieByIdTest() {
		ResponseEntity<MovieDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(MovieIT.movieDTO.getId()), MovieDTO.class);
		MovieDTO movieDTO = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MovieIT.movieDTO, movieDTO);
	}

	@Test
	@Order(5)
	public void editMovieTest() {
		MovieDTO movieDTO = MovieIT.movieDTO;
		movieDTO.setTitle("Nome: O Filme 2");
		movieDTO.setYear(1982);
		movieDTO.setIsWinner(false);
		movieDTO.setFilmStudios("GMO");
		movieDTO.setFilmProducers("ProdutorTres");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<MovieDTO> requestEntity = new HttpEntity<>(movieDTO, headers);
		ResponseEntity<MovieDTO> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(MovieIT.movieDTO.getId()), HttpMethod.PUT, requestEntity, MovieDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(movieDTO, responseEntity.getBody());

		MovieIT.movieDTO = responseEntity.getBody();
	}

	@Test
	@Order(6)
	public void deleteMovieTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Boolean> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(MovieIT.movieDTO.getId()), HttpMethod.DELETE, requestEntity, Boolean.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(7)
	public void getNonExistentMovieTest() {
		ResponseEntity<MovieDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(MovieIT.movieDTO.getId()), MovieDTO.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertNotEquals(MovieIT.movieDTO, responseEntity.getBody());
	}

	public String getEndpoint() {
		return super.getServerURL().append(MOVIES_ENDPOINT).toString();
	}

	public String getEndpoint(Long id) {
		return super.getServerURL().append(MOVIES_ENDPOINT).append("/").append(id).toString();
	}
}
