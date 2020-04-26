package com.truesoft.construction.domain;

import com.truesoft.construction.domain.enumeration.TenderOfferStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Offer.class)
public abstract class Offer_ {

	public static volatile SingularAttribute<Offer, Tender> tender;
	public static volatile SingularAttribute<Offer, Bidder> bidder;
	public static volatile SingularAttribute<Offer, Double> price;
	public static volatile SingularAttribute<Offer, String> description;
	public static volatile SingularAttribute<Offer, Long> id;
	public static volatile SingularAttribute<Offer, TenderOfferStatus> status;

	public static final String TENDER = "tender";
	public static final String BIDDER = "bidder";
	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

