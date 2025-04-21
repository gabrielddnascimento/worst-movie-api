package com.golden.raspbery.awards.dtos;

public class ProducerWinDTO {

	private String producer;
	private Integer year;

	public ProducerWinDTO(String producer, Integer year) {
		this.producer = producer;
		this.year = year;
	}

	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
}
