package io.protone.repository;

import io.protone.domain.*;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTask entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskRepository extends JpaRepository<CRMTask,Long> {

    List<CRMTask> findByNetwork(CORNetwork network);
    List<CRMTask> findByCRMContactAndNetwork(CRMContact crmContact,CORNetwork network);
    CRMTask findOneByCRMContactAndIdAndNetwork(CRMContact crmContact,Long id,CORNetwork network);
    void deleteByCRMContactAndIdAndNetwork(CRMContact crmContact,Long id,CORNetwork network);
}
