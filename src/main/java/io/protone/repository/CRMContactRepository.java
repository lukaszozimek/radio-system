package io.protone.repository;

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
    CRMContact findOneByShortNameAndNetwork(String shortName,CORNetwork network);
    void deleteByShortNameAndNetwork(String shortName,CORNetwork network);
}
