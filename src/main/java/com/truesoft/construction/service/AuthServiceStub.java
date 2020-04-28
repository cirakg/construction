package com.truesoft.construction.service;

import org.springframework.stereotype.Service;

import com.truesoft.construction.domain.Bidder;
import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.domain.Tender;
import com.truesoft.construction.repository.BidderRepository;
import com.truesoft.construction.repository.IssuerRepository;

import io.undertow.util.BadRequestException;

@Service
/**
 * Stub class for returning users (issuers, bidders), should be changed with Spring auth manager after security implemented
 * @author marko
 *
 */
public class AuthServiceStub {
	
	private final IssuerRepository issuerRepository;
	private final BidderRepository bidderRepository;
	
	public AuthServiceStub(IssuerRepository issuerRepository, BidderRepository bidderRepository) {
		this.issuerRepository = issuerRepository;
		this.bidderRepository = bidderRepository;
	}

	/**
	 * Helper method to get issuer from db based on id. This should be changed to
	 * get it from spring auth manager
	 * 
	 * @param issuerId
	 * @return
	 * @throws BadRequestException
	 */
	public Issuer getIssuer(Long issuerId) throws BadRequestException {
		return issuerRepository.findById(issuerId)
				.orElseThrow(() -> new BadRequestException("Issuer with id: " + issuerId + " is not present."));
	}

	/**
	 * Helper method to get bidder from db based on id. This should be changed to
	 * get it from spring auth manager
	 * 
	 * @param bidderId
	 * @return
	 * @throws BadRequestException
	 */
	public Bidder getBidder(Long bidderId) throws BadRequestException {
		return bidderRepository.findById(bidderId)
				.orElseThrow(() -> new BadRequestException("Bidder with id: " + bidderId + " is not present."));
	}
	
}
