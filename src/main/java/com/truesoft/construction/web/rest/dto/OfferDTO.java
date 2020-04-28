package com.truesoft.construction.web.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OfferDTO extends UIRequestBidder {

	@NotEmpty(message = "Please provide a description")
	private String description;

	@NotNull(message = "Please provide a price")
	private Double price;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
