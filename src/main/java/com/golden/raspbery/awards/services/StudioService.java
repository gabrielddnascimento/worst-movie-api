package com.golden.raspbery.awards.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspbery.awards.dtos.StudioDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.models.Studio;
import com.golden.raspbery.awards.repositories.StudioRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class StudioService {

	private StudioRepository studioRepository;

	public List<StudioDTO> listStudios() {
		Iterable<Studio> studioIterable = this.studioRepository.findAll();

		List<StudioDTO> studioDTOList = new ArrayList<>();
		studioIterable.forEach((studio) -> {
			studioDTOList.add(new StudioDTO(studio));
		});

		return studioDTOList;
	}

	public StudioDTO findStudioById(Long id) throws BusinessException {
		Optional<Studio> studioOptional = this.studioRepository.findById(id);

		if (studioOptional.isEmpty()) {
			throw ExceptionHelper.studioNotFound(id);
		}

		return new StudioDTO(studioOptional.get());
	}

	public StudioDTO registerStudio(StudioDTO studioDTO) throws BusinessException {
		if (StringUtils.isEmpty(studioDTO.getName())) {
			throw ExceptionHelper.studioNameSentNotExistent();
		}

		Optional<Studio> studioOptional = this.studioRepository.findStudioByName(studioDTO.getName());
		if (!studioOptional.isEmpty()) {
			throw ExceptionHelper.studioAlreadyExistent();
		}

		Studio studio = new Studio();
		studio.setName(studioDTO.getName());

		studio = this.studioRepository.save(studio);
		return new StudioDTO(studio);
	}

	public StudioDTO editStudio(StudioDTO studioDTO) throws BusinessException {
		if (StringUtils.isEmpty(studioDTO.getName())) {
			throw ExceptionHelper.studioNameSentNotExistent();
		}

		Optional<Studio> studioOptional = this.studioRepository.findById(studioDTO.getId());

		if (studioOptional.isEmpty()) {
			throw ExceptionHelper.studioNotFound(studioDTO.getId());
		}

		Studio studio = studioOptional.get();
		studio.setName(studioDTO.getName());

		studio = this.studioRepository.save(studio);
		return new StudioDTO(studio);
	}

	public void deleteStudio(Long id) {
		this.studioRepository.deleteById(id);
	}

	@Autowired
	public void setStudioRepository(StudioRepository studioRepository) {
		this.studioRepository = studioRepository;
	}
}
