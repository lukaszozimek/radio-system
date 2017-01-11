package io.protone.repository;

import io.protone.domain.CRMLeadSource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMLeadSource entity.
 */
@SuppressWarnings("unused")
public interface CRMLeadSourceRepository extends JpaRepository<CRMLeadSource,Long> {

}
