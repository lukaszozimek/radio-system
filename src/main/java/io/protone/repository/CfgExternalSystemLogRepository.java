package io.protone.repository;

import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContactStatus;
import io.protone.domain.enumeration.CfgLogTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CfgExternalSystemLog entity.
 */
@SuppressWarnings("unused")
public interface CfgExternalSystemLogRepository extends JpaRepository<CfgExternalSystemLog, Long> {

    List<CfgExternalSystemLog> findByNetworkAndLogColumn(CorNetwork corNetwork, CfgLogTypeEnum logTypeEnum);

    CfgExternalSystemLog findOneByIdAndNetworkAndLogColumn(Long id, CorNetwork corNetwork, CfgLogTypeEnum logTypeEnum);
}
