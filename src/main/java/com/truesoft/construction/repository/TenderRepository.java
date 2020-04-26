package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Tender;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tender entity.
 */
@Repository
public interface TenderRepository extends JpaRepository<Tender, Long> {
}
