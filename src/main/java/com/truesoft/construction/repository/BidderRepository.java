package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Bidder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bidder entity.
 */
@Repository
public interface BidderRepository extends JpaRepository<Bidder, Long> {
}
