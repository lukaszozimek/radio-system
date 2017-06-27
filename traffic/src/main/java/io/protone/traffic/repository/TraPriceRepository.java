package io.protone.traffic.repository;


import io.protone.traffic.domain.TraPrice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraPrice entity.
 */
@SuppressWarnings("unused")
public interface TraPriceRepository extends JpaRepository<TraPrice,Long> {

}
