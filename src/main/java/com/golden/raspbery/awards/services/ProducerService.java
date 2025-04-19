package com.golden.raspbery.awards.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspbery.awards.dtos.ProducerDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.models.Producer;
import com.golden.raspbery.awards.repositories.ProducerRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class ProducerService {

	private ProducerRepository producerRepository;

	public List<ProducerDTO> listProducers() {
		Iterable<Producer> producerIterable = this.producerRepository.findAll();

		List<ProducerDTO> producerDTOList = new ArrayList<>();
		producerIterable.forEach((producer) -> {
			producerDTOList.add(new ProducerDTO(producer));
		});

		return producerDTOList;
	}

	public ProducerDTO findProducerById(Long id) throws BusinessException {
		Optional<Producer> producerOptional = this.producerRepository.findById(id);

		if (producerOptional.isEmpty()) {
			throw ExceptionHelper.producerNotFound(id);
		}

		return new ProducerDTO(producerOptional.get());
	}

	public ProducerDTO registerProducer(ProducerDTO producerDTO) throws BusinessException {
		if (StringUtils.isEmpty(producerDTO.getName())) {
			throw ExceptionHelper.producerNameSentNotExistent();
		}

		Producer existentProducer = this.producerRepository.findProducerByName(producerDTO.getName());
		if (existentProducer != null) {
			throw ExceptionHelper.producerAlreadyExistent();
		}

		Producer producer = new Producer();
		producer.setName(producerDTO.getName());

		producer = this.producerRepository.save(producer);
		return new ProducerDTO(producer);
	}

	public ProducerDTO editProducer(ProducerDTO producerDTO) throws BusinessException {
		if (StringUtils.isEmpty(producerDTO.getName())) {
			throw ExceptionHelper.producerNameSentNotExistent();
		}

		Optional<Producer> producerOptional = this.producerRepository.findById(producerDTO.getId());

		if (producerOptional.isEmpty()) {
			throw ExceptionHelper.producerNotFound(producerDTO.getId());
		}

		Producer producer = producerOptional.get();
		producer.setName(producerDTO.getName());

		producer = this.producerRepository.save(producer);
		return new ProducerDTO(producer);
	}

	public void deleteProducer(Long id) {
		this.producerRepository.deleteById(id);
	}

	@Autowired
	public void setProducerRepository(ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}
}
