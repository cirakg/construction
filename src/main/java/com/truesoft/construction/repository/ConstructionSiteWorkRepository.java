package com.truesoft.construction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.ConstructionWorkCompositeId;

/**
 * Spring Data repository for the ConstructionSiteWork entity.
 */
@Repository
public interface ConstructionSiteWorkRepository
		extends JpaRepository<ConstructionSiteWork, ConstructionWorkCompositeId> {
}
