package com.golden.raspbery.awards.dtos;

import java.util.Objects;

public class MovieDTO {

	private Long id;
	private String title;
	private Integer yearNominated;
	private Boolean isWinner;
	private String filmProducers;
	private String filmStudios;

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

	public Integer getYearNominated() {
		return yearNominated;
	}

	public void setYearNominated(Integer yearNominated) {
		this.yearNominated = yearNominated;
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
				&& Objects.equals(id, other.id) && Objects.equals(isWinner, other.isWinner)
				&& Objects.equals(title, other.title) && Objects.equals(yearNominated, other.yearNominated);
	}


}
