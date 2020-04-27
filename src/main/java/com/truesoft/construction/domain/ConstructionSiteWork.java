package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.truesoft.construction.domain.enumeration.ConstructionSiteWorkStatus;

/**
 * A ConstructionSiteWork.
 */
@Entity
@Table(name = "construction_site_work")
public class ConstructionSiteWork implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConstructionWorkCompositeId id;

	@NotNull
	@Column(name = "date_created", nullable = false)
	private Instant dateCreated;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ConstructionSiteWorkStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("constructionId")
	private ConstructionSite constructionSite;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("workId")
	private Work work;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("tenderId")
	private Tender tender;

	public ConstructionWorkCompositeId getId() {
		return id;
	}

	public void setId(ConstructionWorkCompositeId id) {
		this.id = id;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ConstructionSiteWorkStatus getStatus() {
		return status;
	}

	public void setStatus(ConstructionSiteWorkStatus status) {
		this.status = status;
	}

	public ConstructionSite getConstructionSite() {
		return constructionSite;
	}

	public void setConstructionSite(ConstructionSite constructionSite) {
		this.constructionSite = constructionSite;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public Tender getTender() {
		return tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}

}
