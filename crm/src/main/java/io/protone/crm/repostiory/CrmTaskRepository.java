package io.protone.crm.repostiory;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTask entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskRepository extends JpaRepository<CrmTask, Long> {
    CrmTask findOneByIdAndNetwork(Long id, CorNetwork network);

    @Query("select t from CrmTask as t " +
            "left join fetch t.network as n " +
            "left join fetch t.comments as c " +
            "where n.shortcut = :network and t.id =:id")
    CrmTask findOneByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String network);

    Slice<CrmTask> findSliceByContact_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork, Pageable pageable);

    List<CrmTask> findAllByContact_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork);

    Slice<CrmTask> findSliceByLead_ShortnameAndNetwork_Shortcut(String leadShortName, String corNetwork, Pageable pageable);

    List<CrmTask> findAllByLead_ShortnameAndNetwork_Shortcut(String leadShortName, String corNetworkm);

    Slice<CrmTask> findSliceByOpportunity_ShortNameAndNetwork_Shortcut(String opportunityShortcut, String corNetwork, Pageable pageable);

    Slice<CrmTask> findSliceByAccount_ShortNameAndNetwork_Shortcut(String accountShortName, String corNetwork, Pageable pageable);

    List<CrmTask> findAllByAccount_ShortNameAndNetwork_Shortcut(String accountShortName, String corNetwork);

    List<CrmTask> findAllByOpportunity_ShortNameAndNetwork_Shortcut(String accountShortName, String corNetwork);

    List<CrmTask> findAllByNetwork(CorNetwork network, Pageable pageable);

    List<CrmTask> findByNetwork(CorNetwork network);

    void deleteByIdAndNetwork_Shortcut(Long id, String network);

    void deleteByContact_ShortNameAndNetwork_Shortcut(String crmContact, String corNetwork);

    void deleteByAccount_ShortNameAndNetwork_Shortcut(String account, String corNetwork);

    void deleteByLead_ShortnameAndNetwork_Shortcut(String lead, String corNetwork);

    void deleteByOpportunity_ShortNameAndNetwork_Shortcut(String opportunity, String corNetwork);

}
