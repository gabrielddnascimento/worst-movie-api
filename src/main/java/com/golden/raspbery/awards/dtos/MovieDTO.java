package com.golden.raspbery.awards.dtos;

import java.util.Objects;

import com.golden.raspbery.awards.models.Movie;

public class MovieDTO {

	private Long id;
	private String title;
	private Integer year;
	private Boolean isWinner;
	private String filmProducers;
	private String filmStudios;

	public MovieDTO() {}

	public MovieDTO(Movie movie) {
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.year = movie.getYear();
		this.isWinner = movie.getIsWinner();

		StringBuilder producersStringBuilder = new StringBuilder();
		movie.getProducersSet().forEach((producer) -> {
			producersStringBuilder.append(producer.getName());
			producersStringBuilder.append(", ");
		});

		this.filmProducers = producersStringBuilder.substring(0, producersStringBuilder.lastIndexOf(","));

		StringBuilder studiosStringBuilder = new StringBuilder();
		movie.getStudiosSet().forEach((studio) -> {
			studiosStringBuilder.append(studio.getName());
			studiosStringBuilder.append(", ");
		});;

		this.filmStudios = studiosStringBuilder.substring(0, studiosStringBuilder.lastIndexOf(","));
	}

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

	public String getFilmProducers() {
		return filmProducers;
	}

	public void setFilmProducers(String filmProducers) {
		this.filmProducers = filmProducers;
	}

	public String getFilmStudios() {
		return filmStudios;
	}

	public void setFilmStudios(String filmStudios) {
		this.filmStudios = filmStudios;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieDTO other = (MovieDTO) obj;
		return Objects.equals(filmProducers, other.filmProducers) && Objects.equals(filmStudios, other.filmStudios)
				&& Objects.equals(isWinner, other.isWinner)
				&& Objects.equals(title, other.title) && Objects.equals(year, other.year);
	}


}
