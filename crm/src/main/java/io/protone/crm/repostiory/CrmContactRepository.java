package io.protone.crm.repostiory;


import io.protone.crm.domain.CrmContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CrmContact entity.
 */
@SuppressWarnings("unused")
public interface CrmContactRepository extends JpaRepository<CrmContact, Long> {
    CrmContact findOneByShortNameAndNetwork_Shortcut(String shortname, String network);

    Slice<CrmContact> findSliceByNetwork_Shortcut(String network, Pageable pagable);

    void deleteByShortNameAndNetwork_Shortcut(String shortname, String network);
}
