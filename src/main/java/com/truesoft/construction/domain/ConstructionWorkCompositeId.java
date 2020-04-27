package com.truesoft.construction.domain;

import java.io.Serializable;

/**
 * Embeddable class for composite key (Construction,Work)
 * 
 * @author marko
 *
 */
public class ConstructionWorkCompositeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long constructionSiteId;

	private Long workId;

	public ConstructionWorkCompositeId() {
	}

	public ConstructionWorkCompositeId(Long constructionSiteId, Long workId) {
		super();
		this.constructionSiteId = constructionSiteId;
		this.workId = workId;
	}

	public Long getConstructionSiteId() {
		return constructionSiteId;
	}

	public void setConstructionSiteId(Long constructionSiteId) {
		this.constructionSiteId = constructionSiteId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constructionSiteId == null) ? 0 : constructionSiteId.hashCode());
		result = prime * result + ((workId == null) ? 0 : workId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructionWorkCompositeId other = (ConstructionWorkCompositeId) obj;
		if (constructionSiteId == null) {
			if (other.constructionSiteId != null)
				return false;
		} else if (!constructionSiteId.equals(other.constructionSiteId))
			return false;
		if (workId == null) {
			if (other.workId != null)
				return false;
		} else if (!workId.equals(other.workId))
			return false;
		return true;
	}

}
