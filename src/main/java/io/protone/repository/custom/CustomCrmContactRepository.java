package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmContact entity.
 */
@SuppressWarnings("unused")
public interface CustomCrmContactRepository extends JpaRepository<CrmContact, Long> {
    CrmContact findOneByShortNameAndNetwork(String shortname, CorNetwork network);

    List<CrmContact> findByNetwork(CorNetwork network);

    List<CrmContact> findAllByNetwork(CorNetwork network, Pageable pagable);

    void deleteByShortNameAndNetwork(String shortname, CorNetwork network);
}
