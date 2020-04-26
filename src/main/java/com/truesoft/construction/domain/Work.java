package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
public class Work implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull
	@Column(name = "date_created", nullable = false)
	private Instant dateCreated;

	@ManyToOne
	private ConstructionSiteWork constructionSiteWork;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public Work description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public Work dateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public ConstructionSiteWork getConstructionSiteWork() {
		return constructionSiteWork;
	}

	public void setConstructionSiteWork(ConstructionSiteWork constructionSiteWork) {
		this.constructionSiteWork = constructionSiteWork;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Work)) {
			return false;
		}
		return id != null && id.equals(((Work) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Work{" + "id=" + getId() + ", description='" + getDescription() + "'" + ", dateCreated='"
				+ getDateCreated() + "'" + "}";
	}
}
