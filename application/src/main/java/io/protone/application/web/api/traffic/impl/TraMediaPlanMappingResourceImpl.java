package io.protone.application.web.api.traffic.impl;

import io.protone.service.traffic.TraPlaylistMediaPlanMappingService;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.web.api.traffic.TraMediaPlanMappingResource;
import io.protone.web.rest.dto.traffic.TraPlaylistDiffDTO;
import io.protone.web.rest.mapper.TraPlaylistMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraMediaPlanMappingResourceImpl implements TraMediaPlanMappingResource {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanMappingResourceImpl.class);

    @Inject
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Inject
    private TraPlaylistMapper traPlaylistMapper;

    @Override
    public ResponseEntity<TraPlaylistDiffDTO> assigneMediaPlanOnPlaylistsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "mediaPlanId", required = true) @PathVariable("mediaPlanId") Long mediaPlanId,
                                                                                  @ApiParam(value = "advertismentId", required = true) @PathVariable("advertisementId") Long advertismentId) {
        //TODO:// MAPSTRUCT BUG!!
        TraPlaylistDiff traPlaylistDiff = traPlaylistMediaPlanMappingService.mapMediaPlanEntriesToPlaylistWithSelectedAdvertisment(mediaPlanId, advertismentId, networkShortcut, channelShortcut);
        TraPlaylistDiffDTO response = new TraPlaylistDiffDTO();
        response.setParsedFromExcel(traPlaylistMapper.DBs2DTOs(traPlaylistDiff.getParsedFromExcel()));
        response.setEntityPlaylist(traPlaylistMapper.DBs2DTOs(traPlaylistDiff.getEntityPlaylist()));
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
