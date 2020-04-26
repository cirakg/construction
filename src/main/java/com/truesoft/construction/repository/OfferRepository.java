package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Offer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Offer entity.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
