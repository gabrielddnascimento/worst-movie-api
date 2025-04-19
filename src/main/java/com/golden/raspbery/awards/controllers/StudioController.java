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

import com.golden.raspbery.awards.dtos.StudioDTO;
import com.golden.raspbery.awards.infrastructure.BusinessException;
import com.golden.raspbery.awards.infrastructure.ExceptionHelper;
import com.golden.raspbery.awards.services.StudioService;

@Controller
@RequestMapping("/studios")
public class StudioController {

	private StudioService studioService;

	@GetMapping
	public ResponseEntity<List<StudioDTO>> listStudios() {
		List<StudioDTO> studioDTOList = this.studioService.listStudios();

		return ResponseEntity.ok(studioDTOList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudioDTO> findStudioById(@PathVariable Long id) throws BusinessException {
		StudioDTO studioDTO = this.studioService.findStudioById(id);

		return ResponseEntity.ok(studioDTO);
	}

	@PostMapping
	public ResponseEntity<StudioDTO> registerStudio(@RequestBody StudioDTO studioDTO) throws BusinessException {
		if (studioDTO == null) {
			throw ExceptionHelper.studioNotSent();
		}

		studioDTO = this.studioService.registerStudio(studioDTO);

		return ResponseEntity.ok(studioDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudioDTO> editStudio(@RequestBody StudioDTO studioDTO, @PathVariable Long id) throws BusinessException {
		if (studioDTO == null) {
			throw ExceptionHelper.studioNotSent();
		}

		studioDTO.setId(id);
		studioDTO = this.studioService.editStudio(studioDTO);

		return ResponseEntity.ok(studioDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteStudio(@PathVariable Long id) {
		this.studioService.deleteStudio(id);

		return ResponseEntity.ok(Boolean.TRUE);
	}

	@Autowired
	public void setStudioService(StudioService studioService) {
		this.studioService = studioService;
	}
}
