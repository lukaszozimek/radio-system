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
public interface CrmTaskRepository extends JpaRepository<CrmTask, Long> {

    @Query("select t from CrmTask as t " +
            "left join fetch t.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch t.comments as c " +
            "where t.id =:id and org.shortcut =:organization and ch.shortcut =:channelShortcut")
    CrmTask findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    Slice<CrmTask> findSliceByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmContact, String organization, String channelShortcut, Pageable pageable);

    List<CrmTask> findAllByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmContact, String organization, String channelShortcut);

    Slice<CrmTask> findSliceByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(String leadShortName, String organization, String channelShortcut, Pageable pageable);

    List<CrmTask> findAllByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(String leadShortName, String organization, String channelShortcut);

    Slice<CrmTask> findSliceByOpportunity_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String opportunityShortcut, String organization, String channelShortcut, Pageable pageable);

    Slice<CrmTask> findSliceByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String accountShortName, String organization, String channelShortcut, Pageable pageable);

    List<CrmTask> findAllByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String accountShortName, String organization, String channelShortcut);

    List<CrmTask> findAllByOpportunity_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String accountShortName, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmContact, String organization, String channelShortcut);

    void deleteByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String account, String organization, String channelShortcut);

    void deleteByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(String lead, String organization, String channelShortcut);

}
