package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	@Id
	private Long id;

	@NotNull
	@Column(name = "date_created", nullable = false)
	private Instant dateCreated;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ConstructionSiteWorkStatus status;

	@OneToOne(optional = false)
	@NotNull

	@MapsId
	@JoinColumn(name = "id")
	private ConstructionSite constructionSite;

	@OneToMany(mappedBy = "constructionSiteWork")
	private Set<Work> works = new HashSet<>();

	@ManyToOne
	private Tender tender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public ConstructionSiteWork dateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ConstructionSiteWorkStatus getStatus() {
		return status;
	}

	public ConstructionSiteWork status(ConstructionSiteWorkStatus status) {
		this.status = status;
		return this;
	}

	public void setStatus(ConstructionSiteWorkStatus status) {
		this.status = status;
	}

	public ConstructionSite getConstructionSite() {
		return constructionSite;
	}

	public ConstructionSiteWork constructionSite(ConstructionSite constructionSite) {
		this.constructionSite = constructionSite;
		return this;
	}

	public void setConstructionSite(ConstructionSite constructionSite) {
		this.constructionSite = constructionSite;
	}

	public Set<Work> getWorks() {
		return works;
	}

	public ConstructionSiteWork works(Set<Work> works) {
		this.works = works;
		return this;
	}

	public void setWorks(Set<Work> works) {
		this.works = works;
	}

	public Tender getTender() {
		return tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ConstructionSiteWork)) {
			return false;
		}
		return id != null && id.equals(((ConstructionSiteWork) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "ConstructionSiteWork{" + "id=" + getId() + ", dateCreated='" + getDateCreated() + "'" + ", status='"
				+ getStatus() + "'" + "}";
	}
}
