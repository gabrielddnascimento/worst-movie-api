package com.golden.raspbery.awards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.golden.raspbery.awards.dtos.StudioDTO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudioIT extends TestBase {
	private static final String STUDIOS_ENDPOINT = "/studios";

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static StudioDTO studioDTO;

	@Test
	@Order(1)
	public void createStudioTest() {
		StudioDTO studioDTO = new StudioDTO();
		studioDTO.setName("StudioTest");

		ResponseEntity<StudioDTO> responseEntity = this.testRestTemplate.postForEntity(this.getEndpoint(), studioDTO, StudioDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StudioIT.studioDTO = responseEntity.getBody();
		assertNotNull(StudioIT.studioDTO);
		assertEquals(studioDTO.getName(), StudioIT.studioDTO.getName());
	}

	@Test
	@Order(2)
	public void listStudiosTest() {
		ResponseEntity<StudioDTO[]> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(), StudioDTO[].class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StudioDTO[] studiosDTOArray = responseEntity.getBody();
		assertNotNull(studiosDTOArray);
		assert studiosDTOArray.length > 0;
	}

	@Test
	@Order(3)
	public void getStudioByIdTest() {
		ResponseEntity<StudioDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(StudioIT.studioDTO.getId()), StudioDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StudioDTO studioDTO = responseEntity.getBody();

		assertNotNull(studioDTO);
		assert StudioIT.studioDTO.equals(studioDTO);
	}

	@Test
	@Order(4)
	public void editStudioTest() {
		StudioIT.studioDTO.setName("StudioDifferentNameTest");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<StudioDTO> requestEntity = new HttpEntity<>(StudioIT.studioDTO, headers);
		ResponseEntity<StudioDTO> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(StudioIT.studioDTO.getId()), HttpMethod.PUT, requestEntity, StudioDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StudioDTO studioDTO = responseEntity.getBody();

		assertNotNull(studioDTO);
		assert StudioIT.studioDTO.equals(studioDTO);
	}

	@Test
	@Order(5)
	public void deleteStudioTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Boolean> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(StudioIT.studioDTO.getId()), HttpMethod.DELETE, requestEntity, Boolean.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(6)
	public void getNonExistentStudioTest() {
		ResponseEntity<StudioDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(StudioIT.studioDTO.getId()), StudioDTO.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

		StudioDTO studioDTO = responseEntity.getBody();
		assertNull(studioDTO.getId());
	}

	public String getEndpoint() {
		return super.getServerURL().append(STUDIOS_ENDPOINT).toString();
	}

	public String getEndpoint(Long id) {
		return super.getServerURL().append(STUDIOS_ENDPOINT).append("/").append(id).toString();
	}
}
