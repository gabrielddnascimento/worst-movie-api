package com.golden.raspbery.awards.dtos;

import java.util.Objects;

import com.golden.raspbery.awards.models.Producer;

public class ProducerDTO {
	private Long id;
	private String name;

	public ProducerDTO() {}

	public ProducerDTO(Producer producer) {
		this.id = producer.getId();
		this.name = producer.getName();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProducerDTO other = (ProducerDTO) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
	}
}
