package io.protone.repository;

import io.protone.domain.TRABlockPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRABlockPrice entity.
 */
@SuppressWarnings("unused")
public interface TRABlockPriceRepository extends JpaRepository<TRABlockPrice,Long> {

}
