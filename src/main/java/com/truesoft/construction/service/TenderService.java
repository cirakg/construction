package com.truesoft.construction.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.truesoft.construction.domain.Offer;
import com.truesoft.construction.domain.enumeration.TenderOfferStatus;
import com.truesoft.construction.domain.enumeration.TenderStatus;
import com.truesoft.construction.repository.OfferRepository;
import com.truesoft.construction.repository.TenderRepository;

@Service
public class TenderService {

	private final OfferRepository offerRepository;
	private final TenderRepository tenderRepository;

	public TenderService(OfferRepository offerRepository, TenderRepository tenderRepository) {
		this.offerRepository = offerRepository;
		this.tenderRepository = tenderRepository;
	}

	@Transactional
	public synchronized void acceptTenderOffer(Offer offer) {

		List<Offer> offers = offerRepository.findAllByTender(offer.getTender());
		for (Offer o : offers) {
			o.setStatus(TenderOfferStatus.REJECTED);
			offerRepository.save(o);
		}

		offer.setStatus(TenderOfferStatus.ACCEPTED);
		offer.getTender().setStatus(TenderStatus.CLOSED);
		offer = offerRepository.save(offer);
	 	offer.getTender().setStatus(TenderStatus.CLOSED);
		tenderRepository.save(offer.getTender());
	}

}
