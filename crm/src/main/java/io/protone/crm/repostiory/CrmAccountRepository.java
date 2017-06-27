package io.protone.crm.repostiory;

import io.protone.domain.CrmAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmAccount entity.
 */
@SuppressWarnings("unused")
public interface CrmAccountRepository extends JpaRepository<CrmAccount, Long> {
    CrmAccount findOneByShortNameAndNetwork_Shortcut(String shortName, String network);

    List<CrmAccount> findByNetwork_Shortcut(String network);

    List<CrmAccount> findAllByNetwork_Shortcut(String network, Pageable pageable);

    void deleteByShortNameAndNetwork_Shortcut(String shortName, String network);
}
