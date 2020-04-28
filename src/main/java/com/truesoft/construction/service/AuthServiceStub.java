package com.truesoft.construction.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.truesoft.construction.domain.Bidder;
import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.repository.BidderRepository;
import com.truesoft.construction.repository.IssuerRepository;

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
	 */
	public Issuer getIssuer(Long issuerId){
		return issuerRepository.findById(issuerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Issuer with id: " + issuerId + " is not present."));
	}

	/**
	 * Helper method to get bidder from db based on id. This should be changed to
	 * get it from spring auth manager
	 * 
	 * @param bidderId
	 * @return
	 */
	public Bidder getBidder(Long bidderId) {
		return bidderRepository.findById(bidderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bidder with id: " + bidderId + " is not present."));
	}
	
}
