package com.truesoft.construction.domain;

import com.truesoft.construction.domain.enumeration.TenderStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tender.class)
public abstract class Tender_ {

	public static volatile SingularAttribute<Tender, Instant> dateCreated;
	public static volatile SingularAttribute<Tender, Instant> dateStarted;
	public static volatile SingularAttribute<Tender, String> name;
	public static volatile SingularAttribute<Tender, String> description;
	public static volatile SingularAttribute<Tender, Long> id;
	public static volatile SingularAttribute<Tender, Issuer> issuer;
	public static volatile SingularAttribute<Tender, TenderStatus> status;

	public static final String DATE_CREATED = "dateCreated";
	public static final String DATE_STARTED = "dateStarted";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String ISSUER = "issuer";
	public static final String STATUS = "status";

}

