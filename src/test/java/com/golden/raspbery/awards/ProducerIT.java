package com.golden.raspbery.awards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.golden.raspbery.awards.dtos.IntervalAnalysisDTO;
import com.golden.raspbery.awards.dtos.IntervalDTO;
import com.golden.raspbery.awards.dtos.ProducerDTO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProducerIT extends TestBase {

	private static final String PRODUCERS_ENDPOINT = "/producers";

	@Autowired
	private MockMvc mockMvc;

	private static ProducerDTO producerDTO;

	@Test
	@Order(1)
	public void createProducerTest() throws Exception {
		ProducerDTO newProducer = new ProducerDTO();
		newProducer.setName("ProducerTest");

		MvcResult result = mockMvc.perform(post(getEndpoint())
				.contentType(MediaType.APPLICATION_JSON)
				.content(super.toJson(newProducer)))
				.andExpect(status().isOk())
				.andReturn();

		producerDTO = super.fromJson(result.getResponse().getContentAsString(), ProducerDTO.class);
		assertNotNull(producerDTO);
		assertEquals(newProducer.getName(), producerDTO.getName());
	}

	@Test
	@Order(2)
	public void listProducersTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint()))
				.andExpect(status().isOk())
				.andReturn();

		ProducerDTO[] producers = super.fromJson(result.getResponse().getContentAsString(), ProducerDTO[].class);
		assertNotNull(producers);
		assert producers.length > 0;
	}

	@Test
	@Order(3)
	public void getProducerByIdTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint(producerDTO.getId())))
				.andExpect(status().isOk())
				.andReturn();

		ProducerDTO found = super.fromJson(result.getResponse().getContentAsString(), ProducerDTO.class);
		assertNotNull(found);
		assertEquals(producerDTO, found);
	}

	@Test
	@Order(4)
	public void editProducerTest() throws Exception {
		producerDTO.setName("ProducerDifferentNameTest");

		MvcResult result = mockMvc.perform(put(getEndpoint(producerDTO.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(super.toJson(producerDTO)))
				.andExpect(status().isOk())
				.andReturn();

		ProducerDTO updated = super.fromJson(result.getResponse().getContentAsString(), ProducerDTO.class);
		assertNotNull(updated);
		assertEquals(producerDTO, updated);
		producerDTO = updated;
	}

	@Test
	@Order(5)
	public void deleteProducerTest() throws Exception {
		mockMvc.perform(delete(getEndpoint(producerDTO.getId())))
				.andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void getNonExistentProducerTest() throws Exception {
		mockMvc.perform(get(getEndpoint(producerDTO.getId())))
				.andExpect(status().is5xxServerError());
	}

	@Test
	@Order(7)
	public void getIntervalsAnalysisTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getIntervalsAnalysisEndpoint()))
				.andExpect(status().isOk())
				.andReturn();

		IntervalAnalysisDTO returnedIntervalAnalysisDTO = super.fromJson(result.getResponse().getContentAsString(), IntervalAnalysisDTO.class);
		assertNotNull(returnedIntervalAnalysisDTO);

		IntervalAnalysisDTO correctIntervalAnalysisDTO = new IntervalAnalysisDTO();

		IntervalDTO minIntervalDTO = new IntervalDTO();
		minIntervalDTO.setProducerName("Joel Silver");
		minIntervalDTO.setYearsInterval(1);
		minIntervalDTO.setPreviousWinYear(1990);
		minIntervalDTO.setFollowingWinYear(1991);

		IntervalDTO maxIntervalDTO = new IntervalDTO();
		maxIntervalDTO.setProducerName("Matthew Vaughn");
		maxIntervalDTO.setYearsInterval(13);
		maxIntervalDTO.setPreviousWinYear(2002);
		maxIntervalDTO.setFollowingWinYear(2015);

		correctIntervalAnalysisDTO.setMinIntervalsList(Collections.singletonList(minIntervalDTO));
		correctIntervalAnalysisDTO.setMaxIntervalsList(Collections.singletonList(maxIntervalDTO));

		assertEquals(correctIntervalAnalysisDTO, returnedIntervalAnalysisDTO);
	}

	private String getEndpoint() {
		return PRODUCERS_ENDPOINT;
	}

	private String getEndpoint(Long id) {
		return String.format(PRODUCERS_ENDPOINT + "/%d", id);
	}

	private String getIntervalsAnalysisEndpoint() {
		return PRODUCERS_ENDPOINT + "/intervals-analysis";
	}
}
