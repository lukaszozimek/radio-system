package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMAccount;

import io.protone.domain.CRMContact;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMAccount entity.
 */
@SuppressWarnings("unused")
public interface CRMAccountRepository extends JpaRepository<CRMAccount, Long> {

    List<CRMAccount> findByNetwork(CORNetwork network);

    CRMAccount findOneByShortNameAndNetwork(String shortName, CORNetwork network);

    List<CRMAccount> findByShortNameAndNetwork(String shortName, CORNetwork network);

    void deleteByShortNameAndNetwork(String shortName, CORNetwork network);
}
