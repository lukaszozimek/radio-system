package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmLead entity.
 */
@SuppressWarnings("unused")
public interface CrmLeadRepository extends JpaRepository<CrmLead, Long> {
    void deleteByShortnameAndNetwork(String shortName, CorNetwork network);

    List<CrmLead> findByNetwork(CorNetwork network);

    CrmLead findOneByShortnameAndNetwork(String shortName, CorNetwork network);
}
