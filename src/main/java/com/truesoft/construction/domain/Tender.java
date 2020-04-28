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

	private Tender() {
		super();
	}

	public Tender(@NotNull String name, String description, Issuer issuer) {
		super();
		this.name = name;
		this.description = description;
		this.issuer = issuer;
		this.dateCreated = Instant.now();
		this.status = TenderStatus.ACTIVE;
		this.dateStarted = Instant.now(); // TODO we start the tender on creation
	}

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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public Instant getDateStarted() {
		return dateStarted;
	}

	public TenderStatus getStatus() {
		return status;
	}

	public Issuer getIssuer() {
		return issuer;
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

	public void closeTender() {
		this.status = TenderStatus.CLOSED;
	}
}
