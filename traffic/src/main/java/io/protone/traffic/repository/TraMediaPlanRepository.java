package io.protone.traffic.repository;


import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraMediaPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraMediaPlan entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanRepository extends JpaRepository<TraMediaPlan, Long> {
    @Query("select m  from TraMediaPlan as m " +
            "left join fetch m.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraMediaPlan> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select m from TraMediaPlan as m " +
            "left join fetch m.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and c.shortName = :customerShortcut ")
    Slice<TraMediaPlan> findSliceByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("customerShortcut") String customerShortcut, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select m  from TraMediaPlan as m " +
            "left join fetch m.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.discount as disc " +
            "left join fetch c.range as cr " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and m.id = :id ")
    TraMediaPlan findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    List<TraMediaPlan> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndAccount(String organization, String channelShortcut, CrmAccount crmAccount);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
