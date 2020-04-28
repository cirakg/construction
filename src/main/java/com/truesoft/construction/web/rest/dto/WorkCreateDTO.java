package com.truesoft.construction.web.rest.dto;

import javax.validation.constraints.NotEmpty;

public class WorkCreateDTO extends UIRequest {

	@NotEmpty(message = "Please provide a description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

}
