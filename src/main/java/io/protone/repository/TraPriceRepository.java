package io.protone.repository;

import io.protone.domain.TraPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraPrice entity.
 */
@SuppressWarnings("unused")
public interface TraPriceRepository extends JpaRepository<TraPrice,Long> {

}
