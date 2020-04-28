package com.truesoft.construction.web.rest.dto;

import javax.validation.constraints.NotNull;

/**
 * Abstract class for all client requests used for sending issuer details (since
 * no auth implemented on BE)
 * 
 * @author marko
 *
 */
public abstract class UIRequestIssuer {

	@NotNull
	protected Long issuerId;

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

}
