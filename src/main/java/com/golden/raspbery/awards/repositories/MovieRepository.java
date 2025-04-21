package com.golden.raspbery.awards.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.golden.raspbery.awards.dtos.ProducerWinDTO;
import com.golden.raspbery.awards.models.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>{

	@Query("SELECT m FROM Movie m WHERE m.year = :year AND m.isWinner = :isWinner")
	public List<Movie> listMoviesByYearAndIsWinner(@Param("year") int year, @Param("isWinner") boolean isWinner);

	@Query("SELECT m FROM Movie m WHERE m.year = :year AND m.title = :title")
	public Movie findMovieByYearAndTitle(@Param("year") int year, @Param("title") String title);

	@Query(value = "SELECT new com.golden.raspbery.awards.dtos.ProducerWinDTO(p.name, m.year) FROM Movie m JOIN m.producersSet p WHERE m.isWinner = true ORDER BY p.name, m.year")
	public List<ProducerWinDTO> findProducersWins();
}
