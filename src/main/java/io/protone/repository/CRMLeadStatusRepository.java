package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLeadSource;
import io.protone.domain.CRMLeadStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLeadStatus entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadStatusRepository extends JpaRepository<CRMLeadStatus, Long> {
    List<CRMLeadStatus> findByNetwork(CORNetwork network);

    CRMLeadStatus findByNameAndNetwork(String name, CORNetwork network);
}
