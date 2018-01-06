package io.protone.traffic.repository;


import io.protone.traffic.domain.TraPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the TraPrice entity.
 */
@SuppressWarnings("unused")
public interface TraPriceRepository extends JpaRepository<TraPrice, Long> {
    @Query("select p from TraPrice as p " +
            "left join fetch p.lenghtMultiplier as lM " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and p.id =:id")
    TraPrice findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    @Query("select p from TraPrice as p " +
            "left join fetch p.lenghtMultiplier as lM " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraPrice> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
