package com.golden.raspbery.awards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.golden.raspbery.awards.dtos.StudioDTO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudioIT extends TestBase {

	private static final String STUDIOS_ENDPOINT = "/studios";

	@Autowired
	private MockMvc mockMvc;

	private static StudioDTO studioDTO;

	@Test
	@Order(1)
	public void createStudioTest() throws Exception {
		StudioDTO newStudio = new StudioDTO();
		newStudio.setName("MovieTest");

		MvcResult result = mockMvc.perform(post(getEndpoint())
				.contentType(MediaType.APPLICATION_JSON)
				.content(super.toJson(newStudio)))
				.andExpect(status().isOk())
				.andReturn();

		studioDTO = super.fromJson(result.getResponse().getContentAsString(), StudioDTO.class);
		assertNotNull(studioDTO);
		assertEquals(newStudio.getName(), studioDTO.getName());
	}

	@Test
	@Order(2)
	public void listStudiosTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint()))
				.andExpect(status().isOk())
				.andReturn();

		StudioDTO[] studios = super.fromJson(result.getResponse().getContentAsString(), StudioDTO[].class);
		assertNotNull(studios);
		assert studios.length > 0;
	}

	@Test
	@Order(3)
	public void getStudioByIdTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint(studioDTO.getId())))
				.andExpect(status().isOk())
				.andReturn();

		StudioDTO found = super.fromJson(result.getResponse().getContentAsString(), StudioDTO.class);
		assertNotNull(found);
		assertEquals(studioDTO, found);
	}

	@Test
	@Order(4)
	public void editStudioTest() throws Exception {
		studioDTO.setName("StudioDifferentNameTest");

		MvcResult result = mockMvc.perform(put(getEndpoint(studioDTO.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(super.toJson(studioDTO)))
				.andExpect(status().isOk())
				.andReturn();

		StudioDTO updated = super.fromJson(result.getResponse().getContentAsString(), StudioDTO.class);
		assertNotNull(updated);
		assertEquals(studioDTO, updated);
		studioDTO = updated;
	}

	@Test
	@Order(5)
	public void deleteStudioTest() throws Exception {
		mockMvc.perform(delete(getEndpoint(studioDTO.getId())))
				.andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void getNonExistentStudioTest() throws Exception {
		mockMvc.perform(get(getEndpoint(studioDTO.getId())))
				.andExpect(status().is5xxServerError());
	}

	private String getEndpoint() {
		return STUDIOS_ENDPOINT;
	}

	private String getEndpoint(Long id) {
		return String.format(STUDIOS_ENDPOINT + "/%d", id);
	}
}
