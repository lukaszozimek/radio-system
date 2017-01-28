package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLead;
import io.protone.domain.CRMLeadSource;

import io.protone.domain.CRMLeadStatus;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLeadSource entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadSourceRepository extends JpaRepository<CRMLeadSource, Long> {
    List<CRMLeadSource> findByNetwork(CORNetwork network);
    CRMLeadSource findOneByNameAndNetwork(String name, CORNetwork corNetwork);
}
