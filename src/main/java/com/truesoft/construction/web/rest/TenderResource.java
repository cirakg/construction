package com.truesoft.construction.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.ConstructionWorkCompositeId;
import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.domain.Tender;
import com.truesoft.construction.domain.enumeration.TenderStatus;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.IssuerRepository;
import com.truesoft.construction.repository.TenderRepository;
import com.truesoft.construction.web.rest.dto.TenderCreateDTO;

import io.undertow.util.BadRequestException;

/**
 * REST controller for managing {@link com.truesoft.construction.domain.Tender}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TenderResource {

	private final Logger log = LoggerFactory.getLogger(TenderResource.class);

	private final TenderRepository tenderRepository;
	private final IssuerRepository issuerRepository;
	private final ConstructionSiteWorkRepository constructionSiteWorkRepository;

	public TenderResource(TenderRepository tenderRepository,
			ConstructionSiteWorkRepository constructionSiteWorkRepository, IssuerRepository issuerRepository) {
		this.tenderRepository = tenderRepository;
		this.constructionSiteWorkRepository = constructionSiteWorkRepository;
		this.issuerRepository = issuerRepository;
	}

	/**
	 * {@code POST  /tender} : Create a new tender.
	 *
	 * @param TenderCreateDTO the tender to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tender
	 * @throws URISyntaxException
	 * @throws BadRequestException
	 */
	@PostMapping("/tender")
	public ResponseEntity<Tender> createConstructionSiteWork(@Valid @RequestBody TenderCreateDTO tenderCreateDTO)
			throws URISyntaxException, BadRequestException {
		log.debug("REST request to create tender : {}", tenderCreateDTO);

		List<ConstructionWorkCompositeId> constructionSiteWorkIds = tenderCreateDTO.getConstructionSiteWorkIds();

		Tender tender = new Tender();
		tender.setName(tenderCreateDTO.getName());
		tender.setDateCreated(Instant.now());
		tender.setDescription(tenderCreateDTO.getDescription());
		tender.setStatus(TenderStatus.NEW);

		Issuer issuer = issuerRepository.findById(tenderCreateDTO.getIssuerId()).orElseThrow(
				() -> new BadRequestException("Issuer with id: " + tenderCreateDTO.getIssuerId() + " is not present."));
		tender.setIssuer(issuer);
		tender = tenderRepository.save(tender);

		for (ConstructionWorkCompositeId id : constructionSiteWorkIds) {
			ConstructionSiteWork work = constructionSiteWorkRepository.findById(id)
					.orElseThrow(() -> new BadRequestException("Work with id: " + id + " is not present."));

			work.setTender(tender);
			constructionSiteWorkRepository.save(work);
		}

		return ResponseEntity.created(new URI("/api/tender/" + tender.getId())).body(tender);
	}

}
