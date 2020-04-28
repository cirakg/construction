package com.truesoft.construction.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.domain.Work;
import com.truesoft.construction.repository.ConstructionSiteRepository;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.WorkRepository;
import com.truesoft.construction.service.AuthServiceStub;
import com.truesoft.construction.web.rest.dto.ConstructionSiteCreateDTO;
import com.truesoft.construction.web.rest.dto.ConstructionSiteWorkCreateDTO;

/**
 * REST controller for managing
 * {@link com.truesoft.construction.domain.ConstructionSite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConstructionSiteResource {

	private final Logger log = LoggerFactory.getLogger(ConstructionSiteResource.class);

	private final ConstructionSiteRepository constructionSiteRepository;
	private final WorkRepository workRepository;
	private final ConstructionSiteWorkRepository constructionSiteWorkRepository;
	private final AuthServiceStub authServiceStub;

	public ConstructionSiteResource(ConstructionSiteRepository constructionSiteRepository,
			WorkRepository workRepository, ConstructionSiteWorkRepository constructionSiteWorkRepository,
			AuthServiceStub authServiceStub) {
		this.constructionSiteRepository = constructionSiteRepository;
		this.workRepository = workRepository;
		this.constructionSiteWorkRepository = constructionSiteWorkRepository;
		this.authServiceStub = authServiceStub;
	}

	/**
	 * {@code POST  /construction-site} : Create a new construction site.
	 *
	 * @param ConstructionSiteCreateDTO the constructionSite to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tender
	 * @throws URISyntaxException
	 */
	@PostMapping("/construction-site")
	public ResponseEntity<ConstructionSite> createConstructionSite(
			@Valid @RequestBody ConstructionSiteCreateDTO constructionSiteCreateDTO) throws URISyntaxException {
		log.debug("REST request to create construction site : {}", constructionSiteCreateDTO);

		Issuer issuer = authServiceStub.getIssuer(constructionSiteCreateDTO.getIssuerId());

		ConstructionSite site = new ConstructionSite(constructionSiteCreateDTO.getName(),
				constructionSiteCreateDTO.getDescription());
		site = constructionSiteRepository.save(site);
		return ResponseEntity.created(new URI("/api/construction-site/" + site.getId())).body(site);
	}

	/**
	 * {@code POST  /construction-site/id/work} : Add work to construction site.
	 *
	 * @param ConstructionSiteWorkCreateDTO the work to add to site.
	 * @throws URISyntaxException
	 */
	@PostMapping("/construction-site/{id}/work")
	public ResponseEntity<Object> addWorkToConstructionSite(@PathVariable("id") @NotNull Long constructionSiteId,
			@Valid @RequestBody ConstructionSiteWorkCreateDTO dto) throws URISyntaxException {
		log.debug("REST request to add work to construction site: {}", dto);

		// checks valid issuer for managing sites and works
		Issuer issuer = authServiceStub.getIssuer(dto.getIssuerId());

		ConstructionSite constructionSite = constructionSiteRepository.findById(constructionSiteId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Construction site with id: " + constructionSiteId + " is not present."));

		List<String> errors = new ArrayList<>();

		dto.getWorkIds().forEach(workId -> {
			Optional<Work> w = workRepository.findById(workId);
			if (!w.isPresent()) {
				errors.add("Work with id: " + workId + " is not present.");
			} else {
				ConstructionSiteWork csw = new ConstructionSiteWork(constructionSite.getId(), w.get().getId());
				constructionSiteWorkRepository.save(csw);
			}
		});

		return errors.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body(errors);
	}

}
