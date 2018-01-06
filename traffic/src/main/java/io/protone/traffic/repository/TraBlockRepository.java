package io.protone.traffic.repository;

import io.protone.traffic.domain.TraBlock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlock entity.
 */
@SuppressWarnings("unused")
public interface TraBlockRepository extends JpaRepository<TraBlock, Long> {

    List<TraBlock> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    TraBlock findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

}
