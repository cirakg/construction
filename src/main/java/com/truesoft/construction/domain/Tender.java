package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.truesoft.construction.domain.enumeration.TenderStatus;

/**
 * A Tender.
 */
@Entity
@Table(name = "tender")
public class Tender implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@NotNull
	@Column(name = "date_created", nullable = false)
	private Instant dateCreated;

	@Column(name = "date_started")
	private Instant dateStarted;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TenderStatus status;

	@ManyToOne
	private Issuer issuer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Tender name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public Tender description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public Tender dateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Instant getDateStarted() {
		return dateStarted;
	}

	public Tender dateStarted(Instant dateStarted) {
		this.dateStarted = dateStarted;
		return this;
	}

	public void setDateStarted(Instant dateStarted) {
		this.dateStarted = dateStarted;
	}

	public TenderStatus getStatus() {
		return status;
	}

	public Tender status(TenderStatus status) {
		this.status = status;
		return this;
	}

	public void setStatus(TenderStatus status) {
		this.status = status;
	}

	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Tender)) {
			return false;
		}
		return id != null && id.equals(((Tender) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Tender{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
				+ ", dateCreated='" + getDateCreated() + "'" + ", dateStarted='" + getDateStarted() + "'" + ", status='"
				+ getStatus() + "'" + "}";
	}
}
