package io.protone.repository;

import io.protone.domain.CorTax;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorTax entity.
 */
@SuppressWarnings("unused")
public interface CorTaxRepository extends JpaRepository<CorTax,Long> {

}