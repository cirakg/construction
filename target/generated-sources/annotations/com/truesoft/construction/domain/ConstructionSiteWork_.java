package com.truesoft.construction.domain;

import com.truesoft.construction.domain.enumeration.ConstructionSiteWorkStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ConstructionSiteWork.class)
public abstract class ConstructionSiteWork_ {

	public static volatile SingularAttribute<ConstructionSiteWork, Tender> tender;
	public static volatile SingularAttribute<ConstructionSiteWork, Long> constructionSiteId;
	public static volatile SingularAttribute<ConstructionSiteWork, Instant> dateCreated;
	public static volatile SingularAttribute<ConstructionSiteWork, Long> workId;
	public static volatile SingularAttribute<ConstructionSiteWork, ConstructionSiteWorkStatus> status;

	public static final String TENDER = "tender";
	public static final String CONSTRUCTION_SITE_ID = "constructionSiteId";
	public static final String DATE_CREATED = "dateCreated";
	public static final String WORK_ID = "workId";
	public static final String STATUS = "status";

}

