package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
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
@IdClass(ConstructionWorkCompositeId.class)
public class ConstructionSiteWork implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "construction_site_id")
	private Long constructionSiteId;

	@Id
	@Column(name = "work_id")
	private Long workId;

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
	@JoinColumn(name = "tender_id", nullable = false)
	private Tender tender;

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
