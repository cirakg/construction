package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Work;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Work entity.
 */
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
}
