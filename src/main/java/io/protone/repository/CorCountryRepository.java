package io.protone.repository;

import io.protone.domain.CorCountry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorCountry entity.
 */
@SuppressWarnings("unused")
public interface CorCountryRepository extends JpaRepository<CorCountry,Long> {

}