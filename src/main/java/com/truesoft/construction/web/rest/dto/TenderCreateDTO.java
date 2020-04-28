package com.truesoft.construction.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.truesoft.construction.domain.ConstructionWorkCompositeId;

public class TenderCreateDTO extends UIRequest {

	@NotEmpty(message = "Please provide a name")
	private String name;
	@NotEmpty(message = "Please provide a description")
	private String description;
	@NotEmpty
	private List<ConstructionWorkCompositeId> constructionSiteWorkIds;

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

	public List<ConstructionWorkCompositeId> getConstructionSiteWorkIds() {
		return constructionSiteWorkIds;
	}

	public void setConstructionSiteWorkIds(List<ConstructionWorkCompositeId> constructionSiteWorkIds) {
		this.constructionSiteWorkIds = constructionSiteWorkIds;
	}

	public Long getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Long issuerId) {
		this.issuerId = issuerId;
	}

	@Override
	public String toString() {
		return "TenderCreateDTO [name=" + name + ", description=" + description + ", constructionSiteWorkIds="
				+ constructionSiteWorkIds + ", issuerId=" + issuerId + "]";
	}

}
