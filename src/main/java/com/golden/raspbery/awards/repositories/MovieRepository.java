package com.golden.raspbery.awards.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.golden.raspbery.awards.dtos.ProducerWinDTO;
import com.golden.raspbery.awards.models.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	@Query(value = "SELECT new com.golden.raspbery.awards.dtos.ProducerWinDTO(p.name, m.year) FROM Movie m JOIN m.producersSet p WHERE m.isWinner = true ORDER BY p.name, m.year")
	public List<ProducerWinDTO> findProducersWins();
}
