package com.golden.raspbery.awards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.golden.raspbery.awards.models.Studio;

public interface StudioRepository extends CrudRepository<Studio, Long>{

	@Query("SELECT s FROM Studio s WHERE s.name LIKE :name")
	public Optional<Studio> findStudioByName(@Param("name") String name);
}
