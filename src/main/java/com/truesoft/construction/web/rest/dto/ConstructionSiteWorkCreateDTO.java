package com.truesoft.construction.web.rest.dto;

import java.util.List;

public class ConstructionSiteWorkCreateDTO extends UIRequestIssuer {

	private List<Long> workIds;

	public List<Long> getWorkIds() {
		return workIds;
	}

	public void setWorkIds(List<Long> workIds) {
		this.workIds = workIds;
	}

	@Override
	public String toString() {
		return "ConstructionSiteWorkCreateDTO [workIds=" + workIds + ", issuerId=" + issuerId + "]";
	}

}
