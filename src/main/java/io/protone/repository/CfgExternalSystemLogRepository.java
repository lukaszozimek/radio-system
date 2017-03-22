package io.protone.repository;

import io.protone.domain.CfgExternalSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CfgExternalSystemLog entity.
 */
@SuppressWarnings("unused")
public interface CfgExternalSystemLogRepository extends JpaRepository<CfgExternalSystemLog,Long> {

}
