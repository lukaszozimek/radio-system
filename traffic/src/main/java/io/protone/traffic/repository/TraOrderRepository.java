package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface TraOrderRepository extends JpaRepository<TraOrder, Long> {


    @Query("select o  from TraOrder as o " +
            "left join fetch o.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraOrder> findSliceByChannel_Organization_ShortcutAndChannel_ShortName(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select o  from TraOrder as o " +
            "left join fetch o.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and c.shortName=:shortName")
    Slice<TraOrder> findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("shortName") String crmAccount, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    List<TraOrder> findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmAccount, String organization, String channelShortcut);

    @Query("select o  from TraOrder as o " +
            "left join fetch o.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.emissions as em " +
            "left join fetch o.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            "left join fetch o.customer as c where ch.shortcut = :channelShortcut and org.shortcut =:organization and o.id =:id")
    TraOrder findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcutt);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByCustomerAndChannel_Organization_ShortcutAndChannel_Shortcut(CrmAccount customer, String organization, String channelShortcut);
}
