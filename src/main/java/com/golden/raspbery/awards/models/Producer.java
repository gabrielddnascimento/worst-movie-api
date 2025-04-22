package com.golden.raspbery.awards.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCER")
public class Producer {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "producersSet")
	private Set<Movie> moviesSet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMoviesSet() {
		return moviesSet;
	}

	public void setMoviesSet(Set<Movie> moviesSet) {
		this.moviesSet = moviesSet;
	}
}
