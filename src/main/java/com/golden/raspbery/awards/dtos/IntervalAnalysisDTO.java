package com.golden.raspbery.awards.dtos;

import java.util.List;

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
}
