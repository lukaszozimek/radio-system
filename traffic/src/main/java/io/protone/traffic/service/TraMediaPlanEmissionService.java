package io.protone.traffic.service;

import com.google.common.collect.Sets;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.repository.TraMediaPlanEmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraMediaPlanEmissionService {

    @Autowired
    private TraMediaPlanEmissionRepository traEmissionRepository;

    @Transactional
    public Set<TraMediaPlanEmission> saveTraMediaPlanEmissions(Set<TraMediaPlanEmission> traEmissions) {
        return Sets.newHashSet(traEmissionRepository.save(traEmissions));
    }

    @Transactional
    public TraMediaPlanEmission saveTraMediaPlanEmission(TraMediaPlanEmission traEmission) {
        return traEmissionRepository.saveAndFlush(traEmission);
    }

    @Transactional
    public List<TraMediaPlanEmission> findEmissionsByorganizationShortcutAndChannelShortcutAndMediaplanId(String organizationShortcut, String channelShortcut, Long mediaItemId) {
        return traEmissionRepository.findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndMediaPlan_Id(organizationShortcut, channelShortcut, mediaItemId);
    }

    @Transactional
    public void deleteTraMediaPlanEmissions(Set<TraMediaPlanEmission> traEmissions) {
        traEmissionRepository.deleteInBatch(traEmissions);
    }
}
