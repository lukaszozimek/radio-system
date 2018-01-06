package io.protone.traffic.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraInvoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraInvoice entity.
 */
@SuppressWarnings("unused")
public interface TraInvoiceRepository extends JpaRepository<TraInvoice, Long> {

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ca " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.area as car " +
            "left join fetch c.size as css " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraInvoice> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByCustomerAndChannel_Organization_ShortcutAndChannel_Shortcut(CrmAccount crmAccount, String organization, String channelShortcut);

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ica " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.area as car " +
            "left join fetch c.size as css " +
            "left join fetch i.company as comp " +
            "left join fetch i.orders as o " +
            "left join fetch o.advertisment as a " +
            "left join fetch o.status as stat " +
            "left join fetch o.campaign as camp " +
            "left join fetch o.customer as castom " +
            "left join fetch o.emissions as emissions " +
            "left join fetch castom.area as castomr " +
            "left join fetch castom.size as cs " +
            "left join fetch castom.discount as castomdisc " +
            "left join fetch castom.range as castomr " +
            "left join fetch castom.industry as castomind " +
            "left join fetch castom.addres as castomca " +
            "left join fetch castom.range as castomr " +
            "left join fetch castom.area as castomar " +
            "left join fetch castom.size as castomss " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and i.id =:id")
    TraInvoice findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    @Query("select i  from TraInvoice as i " +
            "left join fetch i.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch i.customer as c " +
            "left join fetch c.addres as ca " +
            "left join fetch c.range as cr " +
            "left join fetch c.area as car " +
            "left join fetch c.discount as disc " +
            "left join fetch c.size as css " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and c.shortName =:shortName")
    Slice<TraInvoice> findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("shortName") String customer, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

}
