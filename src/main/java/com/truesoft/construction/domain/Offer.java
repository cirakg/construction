package com.truesoft.construction.domain;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.truesoft.construction.domain.enumeration.TenderOfferStatus;

/**
 * A tender Offer.
 */
@Entity
@Table(name = "offer")
public class Offer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Offer() {
		super();
	}

	public Offer(@NotNull Double price, String description, @NotNull Tender tender, @NotNull Bidder bidder) {
		super();
		this.price = price;
		this.description = description;
		this.tender = tender;
		this.bidder = bidder;
		this.status = TenderOfferStatus.PENDING;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "description")
	private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TenderOfferStatus status;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("offers")
	private Tender tender;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties("offers")
	private Bidder bidder;

	public Long getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public TenderOfferStatus getStatus() {
		return status;
	}

	public Tender getTender() {
		return tender;
	}

	public Bidder getBidder() {
		return bidder;
	}
	
	public void acceptOffer() throws Exception {
		if(this.status == TenderOfferStatus.PENDING) {
			this.status = TenderOfferStatus.ACCEPTED;
		}else {
			throw new Exception("Cannot accept offer which is not in pending status");
		}
	}
	
	public void rejectOffer() throws Exception {
		if(this.status == TenderOfferStatus.PENDING) {
			this.status = TenderOfferStatus.REJECTED;
		}else {
			throw new Exception("Cannot reject offer which is not in pending status");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Offer)) {
			return false;
		}
		return id != null && id.equals(((Offer) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Offer{" + "id=" + getId() + ", price=" + getPrice() + ", description='" + getDescription() + "'"
				+ ", status='" + getStatus() + "'" + "}";
	}
}
