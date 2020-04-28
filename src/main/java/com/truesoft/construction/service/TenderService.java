package com.truesoft.construction.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.truesoft.construction.domain.Offer;
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
	public synchronized void acceptTenderOffer(Offer offer) throws Exception {

		List<Offer> offers = offerRepository.findAllByTender(offer.getTender());
		for (Offer o : offers) {
			if(!o.getId().equals(offer.getId())) {
				o.rejectOffer();
				offerRepository.save(o);
			}
		}

		offer.acceptOffer();
		offer = offerRepository.save(offer);
		offer.getTender().closeTender();
		tenderRepository.save(offer.getTender());
	}

}
