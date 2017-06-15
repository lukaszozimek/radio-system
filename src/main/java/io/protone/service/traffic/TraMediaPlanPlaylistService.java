package io.protone.service.traffic;

import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.repository.traffic.TraMediaPlanPlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Service
public class TraMediaPlanPlaylistService {

    @Inject
    private TraMediaPlanPlaylistRepository traMediaPlanPlaylistRepository;

    @Inject
    private TraBlockService traBlockService;


    @Transactional
    public Set<TraMediaPlanPlaylist> savePlaylist(Set<TraMediaPlanPlaylist> traPlaylistLists) {
        return traPlaylistLists.stream().map(traPlaylistList -> {
            traPlaylistList.playlists(traBlockService.traSaveBlockSet(traPlaylistList.getPlaylists()));
            return traMediaPlanPlaylistRepository.saveAndFlush(traPlaylistList);
        }).collect(toSet());
    }

    @Transactional
    public void deletePlaylist(Set<TraMediaPlanPlaylist> traPlaylistLists) {
        traPlaylistLists.stream().forEach(traPlaylistList -> {
            traBlockService.deleteBlockSet(traPlaylistList.getPlaylists());
            traMediaPlanPlaylistRepository.delete(traPlaylistList);
        });
    }


}
