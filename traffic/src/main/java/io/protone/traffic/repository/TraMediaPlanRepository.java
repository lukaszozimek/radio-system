package io.protone.traffic.repository;


import io.protone.traffic.domain.TraMediaPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraMediaPlan entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanRepository extends JpaRepository<TraMediaPlan, Long> {
    List<TraMediaPlan> findAllByNetwork_ShortcutAndChannel_Shortcut(String shortcut, String channelShortcut, Pageable pageable);

    List<TraMediaPlan> findAllByAccount_ShortNameAndNetwork_ShortcutAndChannel_Shortcut(String customerShortcut, String corNetwork, String corChannel, Pageable pageable);

    TraMediaPlan findByIdAndNetwork_ShortcutAndChannel_Shortcut(Long id, String corNetwork, String corChannel);

    void deleteByIdAndNetwork_ShortcutAndChannel_Shortcut(Long id, String corNetwork, String corChannel);
}
