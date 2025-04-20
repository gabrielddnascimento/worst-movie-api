package com.golden.raspbery.awards.models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOVIE")
public class Movie {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "TITLE",nullable = false)
	private String title;

	@Column(name = "YEAR_NOMINATED",nullable = false)
	private Integer year;

	@Column(name = "WINNER",nullable = false)
	private Boolean isWinner;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "MOVIE_PRODUCERS",
		joinColumns = @JoinColumn(name = "MOVIE_ID"),
		inverseJoinColumns = @JoinColumn(name = "PRODUCER_ID"))
	private Set<Producer> producersSet;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "MOVIE_STUDIOS",
			joinColumns = @JoinColumn(name = "MOVIE_ID"),
			inverseJoinColumns = @JoinColumn(name = "STUDIO_ID"))
	private Set<Studio> studiosSet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Boolean isWinner) {
		this.isWinner = isWinner;
	}

	public Set<Producer> getProducersSet() {
		return producersSet;
	}

	public void setProducersSet(Set<Producer> producersSet) {
		this.producersSet = producersSet;
	}

	public Set<Studio> getStudiosSet() {
		return studiosSet;
	}

	public void setStudiosSet(Set<Studio> studiosSet) {
		this.studiosSet = studiosSet;
	}
}
