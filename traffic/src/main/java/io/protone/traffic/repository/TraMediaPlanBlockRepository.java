package io.protone.traffic.repository;

import io.protone.traffic.domain.TraMediaPlanBlock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 16/08/2017.
 */
public interface TraMediaPlanBlockRepository extends JpaRepository<TraMediaPlanBlock, Long> {
    
    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    List<TraMediaPlanBlock> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndMediaPlan_Id(String organization, String channelShortcut, Long id);


}
