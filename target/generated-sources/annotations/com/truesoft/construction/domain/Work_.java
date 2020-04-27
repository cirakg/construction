package com.truesoft.construction.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Work.class)
public abstract class Work_ {

	public static volatile SingularAttribute<Work, Instant> dateCreated;
	public static volatile SingularAttribute<Work, String> description;
	public static volatile SingularAttribute<Work, Long> id;

	public static final String DATE_CREATED = "dateCreated";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

