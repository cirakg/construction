package com.truesoft.construction.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.domain.Work;
import com.truesoft.construction.repository.WorkRepository;
import com.truesoft.construction.service.AuthServiceStub;
import com.truesoft.construction.web.rest.dto.WorkCreateDTO;

/**
 * REST controller for managing {@link com.truesoft.construction.domain.Work}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkResource {

	private final Logger log = LoggerFactory.getLogger(WorkResource.class);

	private final AuthServiceStub authServiceStub;
	private final WorkRepository workRepository;

	public WorkResource(WorkRepository workRepository, AuthServiceStub authServiceStub) {
		this.workRepository = workRepository;
		this.authServiceStub = authServiceStub;
	}

	/**
	 * {@code POST  /work} : Create a new work
	 *
	 * @param workCreateDTO the work to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new work
	 * @throws URISyntaxException
	 */
	@PostMapping("/work")
	public ResponseEntity<Work> createConstructionSiteWork(@Valid @RequestBody WorkCreateDTO workCreateDTO)
			throws URISyntaxException {
		log.debug("REST request to create work : {}", workCreateDTO);

		Issuer issuer = authServiceStub.getIssuer(workCreateDTO.getIssuerId());

		Work work = new Work(workCreateDTO.getDescription());
		work = workRepository.save(work);
		return ResponseEntity.created(new URI("/api/work/" + work.getId())).body(work);
	}

}
