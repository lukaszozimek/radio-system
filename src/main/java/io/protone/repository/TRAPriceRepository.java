package io.protone.repository;

import io.protone.domain.TRAPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAPrice entity.
 */
@SuppressWarnings("unused")
public interface TRAPriceRepository extends JpaRepository<TRAPrice,Long> {

}
