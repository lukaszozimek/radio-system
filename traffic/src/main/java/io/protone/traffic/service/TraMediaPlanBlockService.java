package io.protone.traffic.service;

import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.repository.TraMediaPlanBlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraMediaPlanBlockService {

    @Inject
    private TraMediaPlanBlockRepository traBlockRepository;

    @Inject
    private TraMediaPlanEmissionService traEmissionService;


    @Transactional
    public List<TraMediaPlanBlock> traSaveBlockSet(Set<TraMediaPlanBlock> traBlocks) {
        return traBlockRepository.save(traBlocks);
    }

    @Transactional
    public List<TraMediaPlanBlock> findBlockByorganizationShortcutAndChannelShortcutAndMediaplanId(String organizationShortcut, String channelShortcut, Long mediaItemId) {
        return traBlockRepository.findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndMediaPlan_Id(organizationShortcut, channelShortcut, mediaItemId);
    }

    @Transactional
    public void deleteBlockSet(Set<TraMediaPlanBlock> traBlocks) {

    }


}
