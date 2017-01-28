package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMContact;

import io.protone.domain.CRMStage;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMContact entity.
 */
@SuppressWarnings("unused")
public interface CRMContactRepository extends JpaRepository<CRMContact, Long> {

    List<CRMContact> findByNetwork(CORNetwork network);

    CRMContact findOneByIdAndNetwork(Long id,CORNetwork network);
    CRMContact findOneByShortNameAndNetwork(String shortName,CORNetwork network);
    void deleteByShortNameAndNetwork(String shortName,CORNetwork network);
}
