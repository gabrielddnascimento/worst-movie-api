package com.golden.raspbery.awards.dtos;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntervalAnalysisDTO {

	@JsonProperty("min")
	private List<IntervalDTO> minIntervalsList;

	@JsonProperty("max")
	private List<IntervalDTO> maxIntervalsList;

	public List<IntervalDTO> getMinIntervalsList() {
		return minIntervalsList;
	}

	public void setMinIntervalsList(List<IntervalDTO> minIntervalsList) {
		this.minIntervalsList = minIntervalsList;
	}

	public List<IntervalDTO> getMaxIntervalsList() {
		return maxIntervalsList;
	}

	public void setMaxIntervalsList(List<IntervalDTO> maxIntervalsList) {
		this.maxIntervalsList = maxIntervalsList;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntervalAnalysisDTO other = (IntervalAnalysisDTO) obj;
		return Objects.equals(maxIntervalsList, other.maxIntervalsList)
				&& Objects.equals(minIntervalsList, other.minIntervalsList);
	}

	@Override
	public String toString() {
		return "IntervalAnalysisDTO [minIntervalsList=" + minIntervalsList + ", maxIntervalsList=" + maxIntervalsList
				+ "]";
	}
}
