package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraAdvertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraAdvertisement entity.
 */
@SuppressWarnings("unused")
public interface TraAdvertisementRepository extends JpaRepository<TraAdvertisement, Long> {
    List<TraAdvertisement> findByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut);

    @Query("select a  from TraAdvertisement as a " +
            "left join fetch a.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch a.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraAdvertisement> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select a  from TraAdvertisement as a " +
            "left join fetch a.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch a.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization  and a.id =:id")
    TraAdvertisement findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    @Query("select a  from TraAdvertisement as a " +
            "left join fetch a.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch a.customer as c " +
            "left join fetch c.area as car " +
            "left join fetch c.size as cs " +
            "left join fetch c.range as cr " +
            "left join fetch c.discount as disc " +
            "left join fetch c.industry as ind " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and c.shortName = :shortName")
    List<TraAdvertisement> findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("shortName") String crmAccount, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    List<TraAdvertisement> findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmAccount, String organization, String channelShortcut);
}
