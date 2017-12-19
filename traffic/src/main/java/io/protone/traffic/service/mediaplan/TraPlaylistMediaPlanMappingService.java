package io.protone.traffic.service.mediaplan;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibMediaItemService;
import io.protone.traffic.api.dto.TraMediaPlanAdvertisementAssigneDTO;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.mapper.TraMediaPlanBlockMapper;
import io.protone.traffic.service.TraMediaPlanEmissionService;
import io.protone.traffic.service.TraMediaPlanPlaylistDateService;
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
    private final Logger log = LoggerFactory.getLogger(TraPlaylistMediaPlanMappingService.class);
    private static final String COM_DEFAULT = "com";
    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Inject
    private TraMediaPlanEmissionService traMediaPlanEmissionService;
    @Inject
    private TraMediaPlanPlaylistDateService traMediaPlanPlaylistDateService;

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private LibMediaItemService libMediaItemService;

    @Inject
    private TraMediaPlanBlockMapper traMediaPlanMapperPlaylistMapper;
    @Autowired
    @Qualifier("traDefaultMediaPlanMapping")
    private TraMediaPlanMapping traDefaultMediaPlanMapping;

    @Autowired
    @Qualifier("traFixedFirstPositionMediaPlanMapping")
    private TraMediaPlanMapping traFixedFirstPositionMediaPlanMapping;

    @Autowired
    @Qualifier("traFixedLastPositionMediaPlanMapping")
    private TraMediaPlanMapping traFixedLastPositionMediaPlanMapping;

    public TraPlaylistDiff mapMediaPlanEntriesToPlaylistWithSelectedAdvertisment(TraMediaPlanAdvertisementAssigneDTO assigneDTO, String organizationShortcut, String channelShortcut) {
        LibMediaItem traAdvertisement = libMediaItemService.getMediaItem(organizationShortcut, COM_DEFAULT, assigneDTO.getLibMediaItemIdx());
        if (traAdvertisement == null) {
            return null;
        }
        TraMediaPlan traMediaPlan = traMediaPlanService.getMediaPlan(assigneDTO.getMediaPlanId(), organizationShortcut, channelShortcut);
        List<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDates = traMediaPlanPlaylistDateService.findMediaPlanDatesByorganizationShortcutAndChannelShortcutAndMediaplanId(organizationShortcut, channelShortcut, traMediaPlan.getId());
        List<LocalDate> playListsDates = traMediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());

        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), organizationShortcut, channelShortcut);
        List<TraMediaPlanEmission> traMediaPlanEmissionList = traMediaPlanEmissionService.findEmissionsByorganizationShortcutAndChannelShortcutAndMediaplanId(organizationShortcut, channelShortcut, traMediaPlan.getId());
        if (assigneDTO.isFirstPostion()) {
            return traFixedFirstPositionMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanEmissionList, traAdvertisement);
        }
        if (assigneDTO.isLasPosition()) {
            return traFixedLastPositionMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanEmissionList, traAdvertisement);
        }
        return traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanEmissionList, traAdvertisement);

    }


}
