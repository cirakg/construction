package com.truesoft.construction.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truesoft.construction.domain.Bidder;
import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.ConstructionWorkCompositeId;
import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.domain.Offer;
import com.truesoft.construction.domain.Tender;
import com.truesoft.construction.domain.enumeration.TenderStatus;
import com.truesoft.construction.repository.BidderRepository;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.IssuerRepository;
import com.truesoft.construction.repository.TenderRepository;
import com.truesoft.construction.web.rest.dto.OfferDTO;
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
	private final BidderRepository bidderRepository;
	

	public TenderResource(TenderRepository tenderRepository,
			ConstructionSiteWorkRepository constructionSiteWorkRepository, IssuerRepository issuerRepository, BidderRepository bidderRepository) {
		this.tenderRepository = tenderRepository;
		this.constructionSiteWorkRepository = constructionSiteWorkRepository;
		this.issuerRepository = issuerRepository;
		this.bidderRepository = bidderRepository;
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

	/**
	 * {@code GET  /tender} : get all the tenders.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tenders in body.
	 * @throws BadRequestException
	 */
	@GetMapping("/tender")
	public List<Tender> getAllTenders(@RequestParam(value = "issuerId") @NotNull Long issuerId)
			throws BadRequestException {
		log.debug("REST request to get all tenders for issuer: " + issuerId);
		Issuer issuer = issuerRepository.findById(issuerId)
				.orElseThrow(() -> new BadRequestException("Issuer with id: " + issuerId + " is not present."));
		return tenderRepository.findAllByIssuer(issuer);
	}

	/**
	 * {@code POST  /tender} : Create a new tender.
	 *
	 * @param OfferDTO the tender offer to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new offer
	 * @throws URISyntaxException
	 * @throws BadRequestException
	 */
	@PostMapping("/tender/offer")
	public ResponseEntity<Offer> createOffer(@Valid @RequestBody OfferDTO offerDTO)
			throws URISyntaxException, BadRequestException {
		log.debug("REST request to create tender offer: {}", offerDTO);

		Tender tender = tenderRepository.findById(offerDTO.getTenderId())
				.orElseThrow(() -> new BadRequestException("Tender with id: " + offerDTO.getTenderId() + " is not present."));
		
		if(tender.getStatus() != TenderStatus.ACTIVE) {
			throw new BadRequestException("Tender with id: " + offerDTO.getTenderId() + " is not active.");
		}
		
		Bidder bidder = bidderRepository.findById(offerDTO.getBidderId())
				.orElseThrow(() -> new BadRequestException("Bidder with id: " + offerDTO.getBidderId() + " is not present."));
		
		Offer offer = new Offer(offerDTO.getPrice(), offerDTO.getDescription(), tender, bidder);
		
		return ResponseEntity.created(new URI("/api/offer/" + offer.getId())).body(offer);
	}

}
