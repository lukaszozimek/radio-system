package io.protone.repository;

import io.protone.domain.CrmLead;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmLead entity.
 */
@SuppressWarnings("unused")
public interface CrmLeadRepository extends JpaRepository<CrmLead,Long> {

}
