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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.truesoft.construction.repository.OfferRepository;
import com.truesoft.construction.repository.TenderRepository;
import com.truesoft.construction.service.TenderService;
import com.truesoft.construction.web.rest.dto.OfferDTO;
import com.truesoft.construction.web.rest.dto.TenderCreateDTO;
import com.truesoft.construction.web.rest.dto.UIRequestIssuer;

import io.undertow.util.BadRequestException;

/**
 * REST controller for managing {@link com.truesoft.construction.domain.Tender}.
 */
@RestController
@RequestMapping("/api")
public class TenderResource {

	private final Logger log = LoggerFactory.getLogger(TenderResource.class);

	private final TenderService tenderService;
	private final TenderRepository tenderRepository;
	private final IssuerRepository issuerRepository;
	private final ConstructionSiteWorkRepository constructionSiteWorkRepository;
	private final BidderRepository bidderRepository;
	private final OfferRepository offerRepository;

	public TenderResource(TenderService tenderService, TenderRepository tenderRepository,
			ConstructionSiteWorkRepository constructionSiteWorkRepository, IssuerRepository issuerRepository,
			BidderRepository bidderRepository, OfferRepository offerRepository) {
		this.tenderRepository = tenderRepository;
		this.constructionSiteWorkRepository = constructionSiteWorkRepository;
		this.issuerRepository = issuerRepository;
		this.bidderRepository = bidderRepository;
		this.offerRepository = offerRepository;
		this.tenderService = tenderService;
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
	@Transactional
	public ResponseEntity<Tender> createTender(@Valid @RequestBody TenderCreateDTO tenderCreateDTO)
			throws URISyntaxException, BadRequestException {
		log.debug("REST request to create tender : {}", tenderCreateDTO);

		List<ConstructionWorkCompositeId> constructionSiteWorkIds = tenderCreateDTO.getConstructionSiteWorkIds();

		Tender tender = new Tender();
		tender.setName(tenderCreateDTO.getName());
		tender.setDateCreated(Instant.now());
		tender.setDescription(tenderCreateDTO.getDescription());
		tender.setStatus(TenderStatus.ACTIVE); // TODO should go to NEW first and then started by the issuer
		tender.setDateStarted(Instant.now());
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
	public ResponseEntity<List<Tender>> getAllTenders(@RequestParam(value = "issuerId") @NotNull Long issuerId)
			throws BadRequestException {
		log.debug("REST request to get all tenders for issuer: " + issuerId);
		Issuer issuer = issuerRepository.findById(issuerId)
				.orElseThrow(() -> new BadRequestException("Issuer with id: " + issuerId + " is not present."));
		return ResponseEntity.ok(tenderRepository.findAllByIssuer(issuer));
	}

	/**
	 * {@code POST  /tender/offer} : Create a new tender offer.
	 *
	 * @param OfferDTO the tender offer to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new offer
	 * @throws URISyntaxException
	 * @throws BadRequestException
	 */
	@PostMapping("/tender/{tenderId}/offer")
	public ResponseEntity<Offer> createOffer(@PathVariable("tenderId") @NotNull Long tenderId,
			@Valid @RequestBody OfferDTO offerDTO) throws URISyntaxException, BadRequestException {
		log.debug("REST request to accept tender offer: {}", offerDTO);

		Tender tender = tenderRepository.findById(tenderId)
				.orElseThrow(() -> new BadRequestException("Tender with id: " + tenderId + " is not present."));

		if (tender.getStatus() != TenderStatus.ACTIVE) {
			throw new BadRequestException("Tender with id: " + tenderId + " is not active.");
		}

		Bidder bidder = bidderRepository.findById(offerDTO.getBidderId()).orElseThrow(
				() -> new BadRequestException("Bidder with id: " + offerDTO.getBidderId() + " is not present."));

		Offer offer = new Offer(offerDTO.getPrice(), offerDTO.getDescription(), tender, bidder);
		offer = offerRepository.save(offer);
		return ResponseEntity.created(new URI("/api/offer/" + offer.getId())).body(offer);
	}

	/**
	 * {@code PUT /tender/{tenderId}/offer/{offerId} : Accepts an tender offer.
	 *
	 * @param OfferDTO the tender offer to create.
	 * 
	 * @throws URISyntaxException
	 * @throws BadRequestException
	 */
	@PutMapping("/tender/{tenderId}/offer/{offerId}")
	public ResponseEntity<Offer> acceptOffer(@PathVariable("offerId") @NotNull Long offerId,
			@RequestParam("issuerId") Long issuerId) throws URISyntaxException, BadRequestException {
		log.debug("REST request to accept offer: {}", offerId);

		Issuer issuer = issuerRepository.findById(issuerId).orElseThrow(
				() -> new BadRequestException("Issuer with id: " + issuerId + " is not present."));

		Offer offer = offerRepository.findById(offerId)
				.orElseThrow(() -> new BadRequestException("Offer with id: " + offerId + " is not present."));

		Tender tender = offer.getTender();
		if (tender.getStatus() != TenderStatus.ACTIVE) {
			throw new BadRequestException("Tender with id: " + tender.getId() + " is not active.");
		}

		if (!tender.getIssuer().getId().equals(issuer.getId())) {
			throw new BadRequestException("You cannot accept other issuer's offer");
		}

		tenderService.acceptTenderOffer(offer);

		return ResponseEntity.accepted().build();
	}

	/**
	 * {@code GET /tender/{tenderId}/offer} : get all tender offers
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tenders in body.
	 * @throws BadRequestException
	 */
	@GetMapping("/tender/{tenderId}/offer")
	public ResponseEntity<List<Offer>> getAllOffersForTender(@PathVariable("tenderId") @NotNull Long tenderId,
			@RequestParam("issuerId") Long issuerId) throws BadRequestException {
		log.debug("REST request to get all offers for tender: {}", tenderId);

		Issuer issuer = issuerRepository.findById(issuerId)
				.orElseThrow(() -> new BadRequestException("Issuer with id: " + issuerId + " is not present."));
		Tender tender = tenderRepository.findById(tenderId)
				.orElseThrow(() -> new BadRequestException("Tender with id: " + tenderId + " is not present."));

		if (!tender.getIssuer().getId().equals(issuer.getId())) {
			throw new BadRequestException("Tender with id: " + tenderId + " doesn't belong to this issuer.");
		}

		List<Offer> offersByTender = offerRepository.findAllByTender(tender);

		return ResponseEntity.ok(offersByTender);
	}

	/**
	 * {@code GET /tender/bidder/{bidderId}} : get all the bidder offers (tendr
	 * optional)
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of offers in body.
	 * @throws BadRequestException
	 */
	@GetMapping("/tender/bidder/{bidderId}/offer")
	public ResponseEntity<List<Offer>> getAllOffersForBidder(@PathVariable("bidderId") @NotNull Long bidderId,
			@RequestParam(required = false) Long tenderId) throws BadRequestException {
		log.debug("REST request to get all offers for bidder: " + bidderId);
		Bidder bidder = bidderRepository.findById(bidderId)
				.orElseThrow(() -> new BadRequestException("Bidder with id: " + bidderId + " is not present."));

		if (tenderId != null) {
			Tender tender = tenderRepository.findById(tenderId)
					.orElseThrow(() -> new BadRequestException("Tender with id: " + tenderId + " is not present."));
			return ResponseEntity.ok(offerRepository.findAllByBidderAndTender(bidder, tender));
		}

		return ResponseEntity.ok(offerRepository.findAllByBidder(bidder));
	}

}
