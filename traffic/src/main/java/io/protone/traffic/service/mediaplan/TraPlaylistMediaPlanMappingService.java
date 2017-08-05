package io.protone.traffic.service.mediaplan;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibItemService;
import io.protone.traffic.api.dto.TraMediaPlanAdvertisementAssigneDTO;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanPlaylist;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.mapper.TraMediaPlanMapperPlaylist;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.traffic.service.mediaplan.mapping.TraMediaPlanMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
@Service
public class TraPlaylistMediaPlanMappingService {
    private final Object lockObject = new Object();
    private final Logger log = LoggerFactory.getLogger(TraPlaylistMediaPlanMappingService.class);

    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private LibItemService libItemService;

    @Inject
    private TraMediaPlanMapperPlaylist traMediaPlanMapperPlaylistMapper;
    @Autowired
    @Qualifier("traDefaultMediaPlanMapping")
    private TraMediaPlanMapping traDefaultMediaPlanMapping;

    @Autowired
    @Qualifier("traFixedFirstPositionMediaPlanMapping")
    private TraMediaPlanMapping traFixedFirstPositionMediaPlanMapping;

    @Autowired
    @Qualifier("traFixedLastPositionMediaPlanMapping")
    private TraMediaPlanMapping traFixedLastPositionMediaPlanMapping;

    public TraPlaylistDiff mapMediaPlanEntriesToPlaylistWithSelectedAdvertisment(TraMediaPlanAdvertisementAssigneDTO assigneDTO, String networkShortcut, String channelShortcut) {
        LibMediaItem traAdvertisement = libItemService.getMediaItem(networkShortcut, "com", assigneDTO.getLibMediaItemIdx());
        TraMediaPlan traMediaPlan = traMediaPlanService.getMediaPlan(assigneDTO.getMediaPlanId(), networkShortcut, channelShortcut);
        List<LocalDate> playListsDates = traMediaPlan.getPlaylists().stream().map(TraMediaPlanPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), networkShortcut, channelShortcut);
        List<TraPlaylist> mediaPlanPlaylists = traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists());
        if (assigneDTO.isFirstPostion()) {
            return traFixedFirstPositionMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanPlaylists, traAdvertisement);
        }
        if (assigneDTO.isLasPosition()) {
            return traFixedLastPositionMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanPlaylists, traAdvertisement);
        }
        return traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanPlaylists, traAdvertisement);

    }


}
