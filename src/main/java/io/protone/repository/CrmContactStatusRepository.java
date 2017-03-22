package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContactStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmContactStatus entity.
 */
@SuppressWarnings("unused")
public interface CrmContactStatusRepository extends JpaRepository<CrmContactStatus,Long> {

    List<CrmContactStatus> findByNetwork(CorNetwork corNetwork);

    CrmContactStatus findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}
