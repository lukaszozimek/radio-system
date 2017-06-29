package io.protone.crm.repostiory;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmLead;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmLead entity.
 */
@SuppressWarnings("unused")
public interface CrmLeadRepository extends JpaRepository<CrmLead, Long> {
    void deleteByShortnameAndNetwork(String shortName, CorNetwork network);

    List<CrmLead> findByNetwork(CorNetwork network);

    List<CrmLead> findAllByNetwork_Shortcut(String network, Pageable pageable);

    CrmLead findOneByShortnameAndNetwork(String shortName, CorNetwork network);

    CrmLead findOneByShortnameAndNetwork_Shortcut(String shortcut, String corNetwork);

    void deleteByShortnameAndNetwork_Shortcut(String shortcut, String corNetwork);
}
