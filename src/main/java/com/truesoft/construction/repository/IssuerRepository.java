package com.truesoft.construction.repository;

import com.truesoft.construction.domain.Issuer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Issuer entity.
 */
@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long> {
}
