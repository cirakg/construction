package com.truesoft.construction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A ConstructionSite.
 */
@Entity
@Table(name = "construction_site")
public class ConstructionSite implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConstructionSite() {
		super();
	}

	public ConstructionSite(@NotNull String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ConstructionSite{" + "id=" + getId() + ", name='" + getName() + "'" + ", description='"
				+ getDescription() + "'" + "}";
	}
}
