package com.golden.raspbery.awards.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspbery.awards.dtos.IntervalAnalysisDTO;
import com.golden.raspbery.awards.dtos.IntervalDTO;
import com.golden.raspbery.awards.dtos.ProducerDTO;
import com.golden.raspbery.awards.dtos.ProducerWinDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.models.Producer;
import com.golden.raspbery.awards.repositories.MovieRepository;
import com.golden.raspbery.awards.repositories.ProducerRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class ProducerService {

	private ProducerRepository producerRepository;

	private MovieRepository movieRepository;

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

		Optional<Producer> producerOptional = this.producerRepository.findProducerByName(producerDTO.getName());
		if (!producerOptional.isEmpty()) {
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

	public IntervalAnalysisDTO getMinAndMaxIntervalAnalisys() {

		List<ProducerWinDTO> producerWinDTOsList = this.movieRepository.findProducersWins();

		Map<String, List<Integer>> winsByProducerMap = new HashMap<>();

		for (ProducerWinDTO producerWinDTO : producerWinDTOsList) {
			String producer = producerWinDTO.getProducer();
			Integer year = producerWinDTO.getYear();

			winsByProducerMap.computeIfAbsent(producer, winsList -> new ArrayList<>()).add(year);
		}

		List<IntervalDTO> intervalList = new ArrayList<>();
		for (Map.Entry<String, List<Integer>> entry : winsByProducerMap.entrySet()) {
			List<Integer> wonYearsList = entry.getValue();
			for (int i = 1; i < wonYearsList.size(); i++) {
				int interval = wonYearsList.get(i) - wonYearsList.get(i - 1);
				intervalList.add(new IntervalDTO(entry.getKey(), interval, wonYearsList.get(i - 1), wonYearsList.get(i)));
			}
		}

		int min = intervalList.stream().mapToInt(IntervalDTO::getYearsInterval).min().orElse(0);
		int max = intervalList.stream().mapToInt(IntervalDTO::getYearsInterval).max().orElse(0);

		List<IntervalDTO> minList = intervalList.stream().filter(i -> i.getYearsInterval() == min).collect(Collectors.toList());
		List<IntervalDTO> maxList = intervalList.stream().filter(i -> i.getYearsInterval() == max).collect(Collectors.toList());

		IntervalAnalysisDTO intervalAnalysisDTO = new IntervalAnalysisDTO();
		intervalAnalysisDTO.setMinIntervalsList(minList);
		intervalAnalysisDTO.setMaxIntervalsList(maxList);

		return intervalAnalysisDTO;
	}

	@Autowired
	public void setProducerRepository(ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}

	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
}
