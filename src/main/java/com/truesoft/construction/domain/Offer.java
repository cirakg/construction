package com.truesoft.construction.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.truesoft.construction.domain.enumeration.TenderOfferStatus;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Offer price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TenderOfferStatus getStatus() {
        return status;
    }

    public Offer status(TenderOfferStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TenderOfferStatus status) {
        this.status = status;
    }

    public Tender getTender() {
        return tender;
    }

    public Offer tender(Tender tender) {
        this.tender = tender;
        return this;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public Offer bidder(Bidder bidder) {
        this.bidder = bidder;
        return this;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "Offer{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
