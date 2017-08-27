package io.protone.traffic.repository;

import io.protone.traffic.domain.TraMediaPlanEmission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 16/08/2017.
 */
public interface TraMediaPlanEmissionRepository extends JpaRepository<TraMediaPlanEmission, Long> {
    List<TraMediaPlanEmission> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    List<TraMediaPlanEmission> findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(String newtorkShortcut,String channelShortcut, Long mediaPlanId);
    TraMediaPlanEmission findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}

