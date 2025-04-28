package com.golden.raspbery.awards.dtos;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntervalDTO {

	@JsonProperty("producer")
	private String producerName;

	@JsonProperty("interval")
	private int yearsInterval;

	@JsonProperty("previousWin")
	private int previousWinYear;

	@JsonProperty("followingWin")
	private int followingWinYear;

	public IntervalDTO() {}

	public IntervalDTO(String producerName, int yearsInterval, int previousWinYear, int followingWinYear) {
		super();
		this.producerName = producerName;
		this.yearsInterval = yearsInterval;
		this.previousWinYear = previousWinYear;
		this.followingWinYear = followingWinYear;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public int getYearsInterval() {
		return yearsInterval;
	}

	public void setYearsInterval(int yearsInterval) {
		this.yearsInterval = yearsInterval;
	}

	public int getPreviousWinYear() {
		return previousWinYear;
	}

	public void setPreviousWinYear(int previousWinYear) {
		this.previousWinYear = previousWinYear;
	}

	public int getFollowingWinYear() {
		return followingWinYear;
	}

	public void setFollowingWinYear(int followingWinYear) {
		this.followingWinYear = followingWinYear;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntervalDTO other = (IntervalDTO) obj;
		return followingWinYear == other.followingWinYear && previousWinYear == other.previousWinYear
				&& Objects.equals(producerName, other.producerName) && yearsInterval == other.yearsInterval;
	}

	@Override
	public String toString() {
		return "IntervalDTO [producerName=" + producerName + ", yearsInterval=" + yearsInterval + ", previousWinYear="
				+ previousWinYear + ", followingWinYear=" + followingWinYear + "]";
	}
}
