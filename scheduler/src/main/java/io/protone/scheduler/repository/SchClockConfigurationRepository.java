package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClockConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchClockConfigurationRepository extends JpaRepository<SchClockConfiguration, Long> {

    Slice<SchClockConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    @Query("select clock from SchClockConfiguration as clock " +
            "left join fetch clock.network as n " +
            "left join fetch clock.channel as ch " +
            "left join fetch clock.emissions as clockEmi " +
            "left join fetch clock.events as clockEvent " +
            "left join fetch clockEvent.events as clockEvEv " +
            "left join fetch clockEvEv.events as clockEvEvEmis " +
            "left join fetch clockEvent.emissions as clockEvEvEmi " +
            "where n.shortcut = :network and clock.shortName =:shortName and ch.shortcut= :channelShortcut")
    SchClockConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);
}
