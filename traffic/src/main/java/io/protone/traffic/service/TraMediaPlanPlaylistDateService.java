package io.protone.traffic.service;


import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.repository.TraMediaPlanPlaylistDateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Service
public class TraMediaPlanPlaylistDateService {

    @Inject
    private TraMediaPlanPlaylistDateRepository traMediaPlanPlaylistDateRepository;

    @Inject
    private TraBlockService traBlockService;


    @Transactional
    public Set<TraMediaPlanPlaylistDate> savePlaylist(Set<TraMediaPlanPlaylistDate> traPlaylistLists) {
        return traMediaPlanPlaylistDateRepository.save(traPlaylistLists).stream().collect(Collectors.toSet());
    }

    @Transactional
    public void deletePlaylist(Set<TraMediaPlanPlaylistDate> traPlaylistLists) {
        traMediaPlanPlaylistDateRepository.delete(traPlaylistLists);

    }


    public List<TraMediaPlanPlaylistDate> findMediaPlanDatesByNetworkShortcutAndChannelShortcutAndMediaplanId(String networkShortcut, String channelShortcut, Long id) {
        return traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(networkShortcut, channelShortcut, id);
    }
}
