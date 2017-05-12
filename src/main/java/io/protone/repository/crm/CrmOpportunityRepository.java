package io.protone.repository.crm;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmOpportunity entity.
 */
@SuppressWarnings("unused")
public interface CrmOpportunityRepository extends JpaRepository<CrmOpportunity, Long> {
    List<CrmOpportunity> findByNetwork(CorNetwork network);

    void deleteByNameAndNetwork(String name, CorNetwork network);

    List<CrmOpportunity> findAllByNetwork_Shortcut(String network, Pageable pageable);

    CrmOpportunity findOneByNameAndNetwork(String name, CorNetwork network);

    CrmOpportunity findOneByShortNameAndNetwork_Shortcut(String shortcut, String corNetwork);

    void deleteByShortNameAndNetwork_Shortcut(String shortcut, String corNetwork);
}
