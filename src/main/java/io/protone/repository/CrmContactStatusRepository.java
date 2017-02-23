package io.protone.repository;

import io.protone.domain.CrmContactStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmContactStatus entity.
 */
@SuppressWarnings("unused")
public interface CrmContactStatusRepository extends JpaRepository<CrmContactStatus,Long> {

}
