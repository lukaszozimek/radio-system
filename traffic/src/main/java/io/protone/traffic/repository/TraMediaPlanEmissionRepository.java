package io.protone.traffic.repository;

import io.protone.traffic.domain.TraMediaPlanEmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 16/08/2017.
 */
public interface TraMediaPlanEmissionRepository extends JpaRepository<TraMediaPlanEmission, Long> {
    List<TraMediaPlanEmission> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndMediaPlan_Id(String organization, String channelShortcut, Long mediaPlanId);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}

