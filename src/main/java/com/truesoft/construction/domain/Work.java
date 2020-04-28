package com.truesoft.construction.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
public class Work implements Serializable {

	private static final long serialVersionUID = 1L;

	private Work() {
		super();
	}

	public Work(String description) {
		this.description = description;
		this.dateCreated = Instant.now();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull
	@Column(name = "date_created", nullable = false)
	private Instant dateCreated;

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	@Override
	public String toString() {
		return "Work{" + "id=" + getId() + ", description='" + getDescription() + "'" + ", dateCreated='"
				+ getDateCreated() + "'" + "}";
	}
}
