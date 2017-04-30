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
public interface CustomTraAdvertisementRepository extends JpaRepository<TraAdvertisement, Long> {
    List<TraAdvertisement> findByNetwork(CorNetwork network);
    List<TraAdvertisement>  findAllByNetwork(CorNetwork network, Pageable pageable);
    List<TraAdvertisement> findByCustomerAndNetwork(CrmAccount crmAccount, CorNetwork network);
}
