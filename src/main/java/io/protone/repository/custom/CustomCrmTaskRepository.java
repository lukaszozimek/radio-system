package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.domain.CrmTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTask entity.
 */
@SuppressWarnings("unused")
public interface CustomCrmTaskRepository extends JpaRepository<CrmTask, Long> {
    CrmTask findOneByIdAndNetwork(Long id, CorNetwork network);

    CrmTask findOneByIdAndNetwork_Shortcut(Long id, String network);

    List<CrmTask> findAllByContact_ShortNameAndNetwork(String crmContact, String corNetwork, Pageable pageable);


    List<CrmTask> findAllByAccount_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork, Pageable pageable);

  //  List<CrmTask> findAllByLead_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork, Pageable pageable);

///    List<CrmTask> findAllByOpportunity_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork, Pageable pageable);

    List<CrmTask> findAllByNetwork(CorNetwork network, Pageable pageable);

    List<CrmTask> findByNetwork(CorNetwork network);

    void deleteByIdAndNetwork(Long id, CorNetwork network);
}
