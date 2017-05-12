package io.protone.repository.traffic;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraAdvertisement;
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
