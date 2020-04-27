package com.truesoft.construction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
/**
 * Embeddable class for composite key (Contruction,Work)
 * @author marko
 *
 */
public class ConstructionWorkCompositeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "construction_id")
	private Long constructionId;

	@Column(name = "work_id")
	private Long workId;

	public ConstructionWorkCompositeId() {
		super();
	}

	public ConstructionWorkCompositeId(Long constructionId, Long workId) {
		super();
		this.constructionId = constructionId;
		this.workId = workId;
	}

	public Long getConstructionId() {
		return constructionId;
	}

	public void setConstructionId(Long constructionId) {
		this.constructionId = constructionId;
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
		result = prime * result + ((constructionId == null) ? 0 : constructionId.hashCode());
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
		if (constructionId == null) {
			if (other.constructionId != null)
				return false;
		} else if (!constructionId.equals(other.constructionId))
			return false;
		if (workId == null) {
			if (other.workId != null)
				return false;
		} else if (!workId.equals(other.workId))
			return false;
		return true;
	}

}
