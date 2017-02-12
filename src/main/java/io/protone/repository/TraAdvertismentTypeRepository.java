package io.protone.repository;

import io.protone.domain.TraAdvertismentType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertismentType entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertismentTypeRepository extends JpaRepository<TraAdvertismentType,Long> {

}
