package com.golden.raspbery.awards.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.golden.raspbery.awards.models.Producer;

public interface ProducerRepository extends CrudRepository<Producer, Long> {

	@Query(value = "SELECT p FROM Producer p WHERE p.name = :name")
	public Producer findProducerByName(@Param("name") String name);
}
