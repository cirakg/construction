package com.truesoft.construction.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * Abstract class for all client requests used for sending bidder details (since
 * no auth implemented on BE)
 * 
 * @author marko
 *
 */
public abstract class UIRequestBidder {

	@NotNull
	protected Long bidderId;

	public Long getBidderId() {
		return bidderId;
	}

	public void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}

}
