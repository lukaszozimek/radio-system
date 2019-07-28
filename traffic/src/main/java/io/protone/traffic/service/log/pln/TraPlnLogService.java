package io.protone.traffic.service.log.pln;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibMediaItemService;
import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.domain.TraLogEmission;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.TraOrderService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.log.TraEmissionLogService;
import io.protone.traffic.service.log.TraLogDiff;
import io.protone.traffic.service.log.pln.mapping.TraPlnMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
@Service("traPlnLogService")
@Qualifier("traPlnLogService")
public class TraPlnLogService implements TraEmissionLogService {

    @Inject
    private TraPlnLogParserService traPlnLogParserService;
    @Inject
    private TraPlaylistService traPlaylistService;
    @Inject
    private LibMediaItemService libMediaItemService;
    @Inject
    private TraOrderService traOrderService;

    @Autowired
    @Qualifier("traDefaultPlnMapping")
    private TraPlnMapping defaultMapping;

    @Autowired
    @Qualifier("traFixedFirstPositionPlnMapping")
    private TraPlnMapping positionFirstMapping;

    @Autowired
    @Qualifier("traFixedLastPositionPlnMapping")
    private TraPlnMapping lastPositonMapping;

    @Override
    public TraLogDiff transform(TraEmissionLogDescriptorDTO emissionLogDescriptorDTO, MultipartFile multipartFile, String organizationShortcut, String channelShortcut) throws IOException {
        List<TraLogEmission> traLogEmissionList = traPlnLogParserService.parse(emissionLogDescriptorDTO, multipartFile);
        TraOrder traOrder = traOrderService.getOrder(emissionLogDescriptorDTO.getOrder().getId());
        List<TraPlaylist> traPlaylist = traPlaylistService.getTraPlaylistListInRange(traOrder.getStartDate(), traOrder.getEndDate(), organizationShortcut, channelShortcut);
        LibMediaItem libMediaItem = libMediaItemService.getMediaItem(organizationShortcut, emissionLogDescriptorDTO.getLibMediaItemThinDTO().getLibrary(), emissionLogDescriptorDTO.getLibMediaItemThinDTO().getIdx());
        if (emissionLogDescriptorDTO.isFirstPostion()) {
            return positionFirstMapping.mapToEntityPlaylist(traPlaylist, traLogEmissionList, libMediaItem);
        } else if (emissionLogDescriptorDTO.isLasPosition()) {
            return lastPositonMapping.mapToEntityPlaylist(traPlaylist, traLogEmissionList, libMediaItem);
        } else {
            return defaultMapping.mapToEntityPlaylist(traPlaylist, traLogEmissionList, libMediaItem);
        }

    }
}
