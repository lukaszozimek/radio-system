package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertismentType;

import io.protone.domain.TraOrderStatus;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertismentType entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertismentTypeRepository extends JpaRepository<TraAdvertismentType,Long> {

    List<TraAdvertismentType> findByNetwork(CorNetwork corNetwork);

    TraAdvertismentType findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
