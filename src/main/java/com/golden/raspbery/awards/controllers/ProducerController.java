package com.golden.raspbery.awards.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.golden.raspbery.awards.dtos.IntervalAnalysisDTO;
import com.golden.raspbery.awards.dtos.ProducerDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.services.ProducerService;

@Controller
@RequestMapping("/producers")
public class ProducerController {

	private ProducerService producerService;

	@GetMapping
	public ResponseEntity<List<ProducerDTO>> listProducers() {
		List<ProducerDTO> producerDTOList = this.producerService.listProducers();

		return ResponseEntity.ok(producerDTOList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProducerDTO> findProducerById(@PathVariable Long id) throws BusinessException {
		ProducerDTO producerDTO = this.producerService.findProducerById(id);

		return ResponseEntity.ok(producerDTO);
	}

	@PostMapping
	public ResponseEntity<ProducerDTO> registerProducer(@RequestBody ProducerDTO producerDTO) throws BusinessException {
		if (producerDTO == null) {
			throw ExceptionHelper.producerNotSent();
		}

		producerDTO = this.producerService.registerProducer(producerDTO);

		return ResponseEntity.ok(producerDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProducerDTO> editProducer(@RequestBody ProducerDTO producerDTO, @PathVariable Long id) throws BusinessException {
		if (producerDTO == null) {
			throw ExceptionHelper.producerNotSent();
		}

		producerDTO.setId(id);
		producerDTO = this.producerService.editProducer(producerDTO);

		return ResponseEntity.ok(producerDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProducer(@PathVariable Long id) {
		this.producerService.deleteProducer(id);

		return ResponseEntity.ok(Boolean.TRUE);
	}

	@GetMapping("/intervals-analysis")
	public ResponseEntity<IntervalAnalysisDTO> getMinAndMaxIntervalsAnalisys() {
		IntervalAnalysisDTO intervalAnalysisDTO = this.producerService.getMinAndMaxIntervalAnalisys();

		return ResponseEntity.ok(intervalAnalysisDTO);
	}

	@Autowired
	public void setProducerService(ProducerService producerService) {
		this.producerService = producerService;
	}
}
