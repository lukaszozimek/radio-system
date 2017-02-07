package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmContact entity.
 */
@SuppressWarnings("unused")
public interface CrmContactRepository extends JpaRepository<CrmContact, Long> {
    CrmContact findOneByShortNameAndNetwork(String shortname, CorNetwork network);

    List<CrmContact> findByNetwork(CorNetwork network);

    void deleteByShortNameAndNetwork(String shortname, CorNetwork network);
}
