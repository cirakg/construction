package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Bidder;
import com.truesoft.construction.domain.Offer;
import com.truesoft.construction.domain.Tender;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Offer entity.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	List<Offer> findAllByTender(Tender tender);

	List<Offer> findAllByBidder(Bidder bidder);

	List<Offer> findAllByBidderAndTender(Bidder bidder, Tender tender);
}
