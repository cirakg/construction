package com.truesoft.construction.repository;

import com.truesoft.construction.domain.ConstructionSite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConstructionSite entity.
 */
@Repository
public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, Long> {
}
