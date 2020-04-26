package com.truesoft.construction.repository;

import com.truesoft.construction.domain.ConstructionSiteWork;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConstructionSiteWork entity.
 */
@Repository
public interface ConstructionSiteWorkRepository extends JpaRepository<ConstructionSiteWork, Long> {
}
