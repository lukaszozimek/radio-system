package io.protone.traffic.repository;

import io.protone.traffic.domain.TraMediaPlanBlock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lukaszozimek on 16/08/2017.
 */
public interface TraMediaPlanBlockRepository extends JpaRepository<TraMediaPlanBlock, Long> {

    List<TraMediaPlanBlock> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraMediaPlanBlock findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);

    List<TraMediaPlanBlock> findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(String shortcut, String shortcut1, Long id);


}
