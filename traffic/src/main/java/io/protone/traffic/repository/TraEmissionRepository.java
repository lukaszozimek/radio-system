package io.protone.traffic.repository;

import io.protone.traffic.domain.TraEmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraEmission entity.
 */
@SuppressWarnings("unused")
public interface TraEmissionRepository extends JpaRepository<TraEmission, Long> {
    List<TraEmission> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    TraEmission findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
