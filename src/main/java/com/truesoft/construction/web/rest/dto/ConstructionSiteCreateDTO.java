package com.truesoft.construction.web.rest.dto;

import javax.validation.constraints.NotEmpty;

public class ConstructionSiteCreateDTO extends UIRequestIssuer {

	@NotEmpty(message = "Please provide a name")
	private String name;
	@NotEmpty(message = "Please provide a description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ConstructionSiteCreateDTO [name=" + name + ", description=" + description + ", issuerId=" + issuerId
				+ "]";
	}

}
