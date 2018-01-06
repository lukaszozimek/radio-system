package io.protone.crm.repostiory;


import io.protone.crm.domain.CrmAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmAccount entity.
 */
public interface CrmAccountRepository extends JpaRepository<CrmAccount, Long> {

    @Query("select a from CrmAccount as a " +
            "left join fetch a.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch a.addres as adr " +
            "left join fetch a.area as ar " +
            "left join fetch a.size as s " +
            "left join fetch a.range as r " +
            "left join fetch a.industry as ind " +
            "left join fetch a.discount as disc " +
            "left join fetch a.corImageItem as image " +
            "left join fetch a.keeper as kep  " +
            "left join fetch a.tasks as tasks " +
            "left join fetch a.person as p where ch.shortcut = :channelShortcut and org.shortcut =:organization  and a.shortName =:shortName")
    CrmAccount findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("shortName") String shortName, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    List<CrmAccount> findByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut);

    @Query("select a from CrmAccount as a " +
            "left join fetch a.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch a.addres as adr " +
            "left join fetch a.area as ar " +
            "left join fetch a.size as s " +
            "left join fetch a.discount as disc " +
            "left join fetch a.range as r " +
            "left join fetch a.industry as ind " +
            "left join fetch a.corImageItem as image " +
            "left join fetch a.keeper as kep  " +
            "left join fetch a.person as p where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<CrmAccount> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    void deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortName, String organization, String channelShortcut);
}
