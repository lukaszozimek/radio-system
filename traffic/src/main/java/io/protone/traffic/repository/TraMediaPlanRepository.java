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
            "left join fetch m.network as n " +
            "left join fetch m.channel as cha " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network and m.channel.shortcut = :corChannel")
    Slice<TraMediaPlan> findSliceByNetwork_ShortcutAndChannel_Shortcut(@Param("network") String shortcut, @Param("corChannel") String channelShortcut, Pageable pageable);

    @Query("select m from TraMediaPlan as m " +
            "left join fetch m.network as n " +
            "left join fetch m.channel as cha " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network and m.channel.shortcut = :corChannel and c.shortName = :customerShortcut ")
    Slice<TraMediaPlan> findSliceByAccount_ShortNameAndNetwork_ShortcutAndChannel_Shortcut(@Param("customerShortcut") String customerShortcut, @Param("network") String corNetwork, @Param("corChannel") String corChannel, Pageable pageable);

    @Query("select m  from TraMediaPlan as m " +
            "left join fetch m.network as n " +
            "left join fetch m.channel as cha " +
            "left join fetch m.libFileItem as media " +
            "left join fetch m.account as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.discount as disc " +
            "left join fetch c.range as cr " +
            "left join fetch c.industry as ind " +
            " where n.shortcut = :network and m.channel.shortcut = :corChannel and m.id = :id ")
    TraMediaPlan findByIdAndNetwork_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("network") String corNetwork, @Param("corChannel") String corChannel);

    List<TraMediaPlan> findAllByNetwork_ShortcutAndAccount(String corNetwork, CrmAccount crmAccount);

    void deleteByIdAndNetwork_ShortcutAndChannel_Shortcut(Long id, String corNetwork, String corChannel);
}
