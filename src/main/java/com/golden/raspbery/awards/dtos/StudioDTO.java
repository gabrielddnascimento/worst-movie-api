package com.golden.raspbery.awards.dtos;

import java.util.Objects;

import com.golden.raspbery.awards.models.Studio;

public class StudioDTO {
	private Long id;
	private String name;

	public StudioDTO() {}

	public StudioDTO(Studio studio) {
		this.id = studio.getId();
		this.name = studio.getName();
	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudioDTO other = (StudioDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
}
