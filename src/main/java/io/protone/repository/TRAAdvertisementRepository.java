package io.protone.repository;

import io.protone.domain.TRAAdvertisement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAAdvertisement entity.
 */
@SuppressWarnings("unused")
public interface TRAAdvertisementRepository extends JpaRepository<TRAAdvertisement, Long> {
    TRAAdvertisement findByName(String name);
}
