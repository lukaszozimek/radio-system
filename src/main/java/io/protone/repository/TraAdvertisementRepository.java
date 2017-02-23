package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraAdvertisement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertisement entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertisementRepository extends JpaRepository<TraAdvertisement, Long> {
    List<TraAdvertisement> findByNetwork(CorNetwork network);

    List<TraAdvertisement> findByCustomerAndNetwork(CrmAccount crmAccount, CorNetwork network);
}
