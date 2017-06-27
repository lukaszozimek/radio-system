package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraAdvertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertisement entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertisementRepository extends JpaRepository<TraAdvertisement, Long> {
    List<TraAdvertisement> findByNetwork(CorNetwork network);

    List<TraAdvertisement> findAllByNetwork_Shortcut(String network, Pageable pageable);

    TraAdvertisement findByIdAndNetwork_Shortcut(Long id, String network);

    List<TraAdvertisement> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String network, Pageable pageable);
}
