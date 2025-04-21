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

import com.golden.raspbery.awards.dtos.IntervalAnalysisDTO;
import com.golden.raspbery.awards.dtos.ProducerDTO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProducerIT extends TestBase {
	private static final String PRODUCERS_ENDPOINT = "/producers";

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static ProducerDTO producerDTO;

	@Test
	@Order(1)
	public void createProducerTest() {
		ProducerDTO producerDTO = new ProducerDTO();
		producerDTO.setName("ProducerTest");

		ResponseEntity<ProducerDTO> responseEntity = this.testRestTemplate.postForEntity(this.getEndpoint(), producerDTO, ProducerDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		ProducerIT.producerDTO = responseEntity.getBody();
		assertNotNull(ProducerIT.producerDTO);
		assertEquals(producerDTO.getName(), ProducerIT.producerDTO.getName());
	}

	@Test
	@Order(2)
	public void listProducersTest() {
		ResponseEntity<ProducerDTO[]> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(), ProducerDTO[].class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		ProducerDTO[] producersDTOArray = responseEntity.getBody();
		assertNotNull(producersDTOArray);
		assert producersDTOArray.length > 0;
	}

	@Test
	@Order(3)
	public void getProducerByIdTest() {
		ResponseEntity<ProducerDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(ProducerIT.producerDTO.getId()), ProducerDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		ProducerDTO producerDTO = responseEntity.getBody();

		assertNotNull(producerDTO);
		assert ProducerIT.producerDTO.equals(producerDTO);
	}

	@Test
	@Order(4)
	public void editProducerTest() {
		ProducerIT.producerDTO.setName("ProducerDifferentNameTest");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ProducerDTO> requestEntity = new HttpEntity<>(ProducerIT.producerDTO, headers);
		ResponseEntity<ProducerDTO> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(ProducerIT.producerDTO.getId()), HttpMethod.PUT, requestEntity, ProducerDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		ProducerDTO producerDTO = responseEntity.getBody();

		assertNotNull(producerDTO);
		assert ProducerIT.producerDTO.equals(producerDTO);
	}

	@Test
	@Order(5)
	public void deleteProducerTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Boolean> responseEntity = this.testRestTemplate.exchange(this.getEndpoint(ProducerIT.producerDTO.getId()), HttpMethod.DELETE, requestEntity, Boolean.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@Order(6)
	public void getNonExistentProducerTest() {
		ResponseEntity<ProducerDTO> responseEntity = this.testRestTemplate.getForEntity(this.getEndpoint(ProducerIT.producerDTO.getId()), ProducerDTO.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

		ProducerDTO producerDTO = responseEntity.getBody();
		assertNull(producerDTO.getId());
	}

	@Test
	@Order(7)
	public void getIntervalsAnalysisTest() {
		ResponseEntity<IntervalAnalysisDTO> responseEntity = this.testRestTemplate.getForEntity(this.getIntervalsAnalisysEndpoint(), IntervalAnalysisDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		IntervalAnalysisDTO intervalAnalisysDTO = responseEntity.getBody();
		assertNotNull(intervalAnalisysDTO);
	}

	public String getEndpoint() {
		return super.getServerURL().append(PRODUCERS_ENDPOINT).toString();
	}

	public String getEndpoint(Long id) {
		return super.getServerURL().append(PRODUCERS_ENDPOINT).append("/").append(id).toString();
	}

	public String getIntervalsAnalisysEndpoint() {
		return super.getServerURL().append(PRODUCERS_ENDPOINT).append("/intervals-analysis").toString();
	}
}
