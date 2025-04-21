package com.golden.raspbery.awards.dtos;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.golden.raspbery.awards.infrastructure.YesNoToBooleanConverter;
import com.golden.raspbery.awards.models.Movie;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

public class MovieDTO {

	private Long id;

	@CsvBindByName(column = "title")
	private String title;

	@CsvBindByName(column = "year")
	private Integer year;

	@CsvCustomBindByName(column = "winner", converter = YesNoToBooleanConverter.class)
	private Boolean isWinner;

	@CsvBindByName(column = "producers")
	private String movieProducers;

	@CsvBindByName(column = "studios")
	private String movieStudios;

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

		this.movieProducers = producersStringBuilder.substring(0, producersStringBuilder.lastIndexOf(","));

		StringBuilder studiosStringBuilder = new StringBuilder();
		movie.getStudiosSet().forEach((studio) -> {
			studiosStringBuilder.append(studio.getName());
			studiosStringBuilder.append(", ");
		});;

		this.movieStudios = studiosStringBuilder.substring(0, studiosStringBuilder.lastIndexOf(","));
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

	public String getMovieProducers() {
		return movieProducers;
	}

	public void setMovieProducers(String filmProducers) {
		this.movieProducers = filmProducers;
	}

	public String getMovieStudios() {
		return movieStudios;
	}

	public void setMovieStudios(String filmStudios) {
		this.movieStudios = filmStudios;
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

		Set<String> thisProducersSet = null;
		Set<String> otherProducersSet = null;
		Set<String> thisStudiosSet = null;
		Set<String> otherStudiosSet = null;

		if (this.movieProducers != null) {
			thisProducersSet = Arrays.stream(this.movieProducers.split(",")).map(String::trim).collect(Collectors.toSet());
		}

		if (other.movieProducers != null) {
			otherProducersSet = Arrays.stream(other.movieProducers.split(",")).map(String::trim).collect(Collectors.toSet());
		}

		if (this.movieStudios != null) {
			thisStudiosSet = Arrays.stream(this.movieStudios.split(",")).map(String::trim).collect(Collectors.toSet());
		}

		if (other.movieStudios != null) {
			otherStudiosSet = Arrays.stream(other.movieStudios.split(",")).map(String::trim).collect(Collectors.toSet());
		}

		return Objects.equals(thisStudiosSet, otherStudiosSet) && Objects.equals(thisProducersSet, otherProducersSet)
				&& Objects.equals(isWinner, other.isWinner) && Objects.equals(title, other.title)
				&& Objects.equals(year, other.year);
	}


}
