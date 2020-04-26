package com.truesoft.construction.domain;

import com.truesoft.construction.domain.enumeration.ConstructionSiteWorkStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ConstructionSiteWork.class)
public abstract class ConstructionSiteWork_ {

	public static volatile SingularAttribute<ConstructionSiteWork, ConstructionSite> constructionSite;
	public static volatile SingularAttribute<ConstructionSiteWork, Tender> tender;
	public static volatile SingularAttribute<ConstructionSiteWork, Instant> dateCreated;
	public static volatile SetAttribute<ConstructionSiteWork, Work> works;
	public static volatile SingularAttribute<ConstructionSiteWork, Long> id;
	public static volatile SingularAttribute<ConstructionSiteWork, ConstructionSiteWorkStatus> status;

	public static final String CONSTRUCTION_SITE = "constructionSite";
	public static final String TENDER = "tender";
	public static final String DATE_CREATED = "dateCreated";
	public static final String WORKS = "works";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

