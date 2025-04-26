package com.golden.raspbery.awards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.golden.raspbery.awards.dtos.MovieDTO;

@TestMethodOrder(value = OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieIT extends TestBase {

	private static final String MOVIES_ENDPOINT = "/movies";

	private static MovieDTO movieDTO;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void createMovieTest() throws Exception {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setTitle("Nome: O Filme");
		movieDTO.setYear(1900);
		movieDTO.setIsWinner(true);
		movieDTO.setMovieStudios("Alpha Video, Cinemark");
		movieDTO.setMovieProducers("ProdutorUm, ProdutorDois");

		MvcResult mvcResult = this.mockMvc.perform(post(this.getEndpoint())
				.content(super.toJson(movieDTO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String jsonResult = mvcResult.getResponse().getContentAsString();
		MovieIT.movieDTO = super.fromJson(jsonResult, MovieDTO.class);

		assertEquals(MovieIT.movieDTO, movieDTO);
	}

	@Test
	@Order(2)
	public void listMoviesTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint())).andExpect(status().isOk()).andReturn();

		MovieDTO[] movieDTOs = super.fromJson(result.getResponse().getContentAsString(), MovieDTO[].class);
		assertTrue(movieDTOs.length > 0);
	}

	@Test
	@Order(3)
	public void findMovieByIdTest() throws Exception {
		MvcResult result = mockMvc.perform(get(getEndpoint(movieDTO.getId()))).andExpect(status().isOk()).andReturn();

		MovieDTO found = super.fromJson(result.getResponse().getContentAsString(), MovieDTO.class);
		assertEquals(movieDTO, found);
	}

	@Test
	@Order(4)
	public void editMovieTest() throws Exception {
		movieDTO.setTitle("Nome: O Filme 2");
		movieDTO.setYear(1982);
		movieDTO.setIsWinner(false);
		movieDTO.setMovieStudios("GMO");
		movieDTO.setMovieProducers("ProdutorTres");

		MvcResult result = mockMvc.perform(put(getEndpoint(movieDTO.getId())).contentType(MediaType.APPLICATION_JSON)
				.content(super.toJson(movieDTO))).andExpect(status().isOk()).andReturn();

		MovieDTO updated = super.fromJson(result.getResponse().getContentAsString(), MovieDTO.class);
		assertEquals(movieDTO, updated);
		movieDTO = updated;
	}

	@Test
	@Order(5)
	public void deleteMovieTest() throws Exception {
		mockMvc.perform(delete(getEndpoint(movieDTO.getId()))).andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void getNonExistentMovieTest() throws Exception {
		mockMvc.perform(get(getEndpoint(movieDTO.getId()))).andExpect(status().is5xxServerError());
	}

	@Test
	@Order(7)
	public void registerMovieFromCSVTest() throws Exception {
		String csvMock = "year;title;studios;producers;winner\n"
				+ "1800;Waterworld;Universal Pictures;Lawrence Gordon;\n"
				+ "1801;Barb Wire;PolyGram Filmed Entertainment;Todd Moyer;yes\n"
				+ "1802;Batman & Robin;Warner Bros.;Peter MacGregor-Scott;\n"
				+ "1812;Godzilla;TriStar Pictures;Dean Devlin;yes\n" + "1817;Wild Wild West;Warner Bros.;Jon Peters;\n";

		MockMultipartFile file = new MockMultipartFile("file", "mock.csv", "text/csv", csvMock.getBytes());

		MvcResult result = mockMvc.perform(multipart(getRegisterFromCSVEndpoint()).file(file))
				.andExpect(status().isOk()).andReturn();

		MovieDTO[] movieDTOs = super.fromJson(result.getResponse().getContentAsString(), MovieDTO[].class);
		assert(movieDTOs.length > 0);

		for (MovieDTO dto : movieDTOs) {
			mockMvc.perform(get(getEndpoint(dto.getId()))).andExpect(status().isOk());

			mockMvc.perform(delete(getEndpoint(dto.getId()))).andExpect(status().isOk());
		}
	}

	private String getEndpoint() {
		return MOVIES_ENDPOINT;
	}

	private String getEndpoint(Long id) {
		return String.format(MOVIES_ENDPOINT + "/%d", id);
	}

	private String getRegisterFromCSVEndpoint() {
		return MOVIES_ENDPOINT + "/csv";
	}
}
