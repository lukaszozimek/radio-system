package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmAccount entity.
 */
@SuppressWarnings("unused")
public interface CrmAccountRepository extends JpaRepository<CrmAccount, Long> {
    CrmAccount findOneByShortNameAndNetwork(String shortName, CorNetwork network);

    List<CrmAccount> findByNetwork(CorNetwork network);

    void deleteByShortNameAndNetwork(String shortName, CorNetwork network);
}
