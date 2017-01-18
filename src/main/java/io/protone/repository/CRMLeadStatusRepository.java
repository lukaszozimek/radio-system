package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLeadStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLeadStatus entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadStatusRepository extends JpaRepository<CRMLeadStatus, Long> {
    CRMLeadStatus findByName(String name);
}
