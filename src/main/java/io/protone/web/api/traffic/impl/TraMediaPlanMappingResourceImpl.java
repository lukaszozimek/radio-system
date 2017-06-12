package io.protone.web.api.traffic.impl;

import io.protone.service.traffic.TraPlaylistMediaPlanMappingService;
import io.protone.web.api.traffic.TraMediaPlanMappingResource;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraMediaPlanMappingResourceImpl implements TraMediaPlanMappingResource {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanMappingResourceImpl.class);

    @Autowired
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Override
    public ResponseEntity<TraPlaylistMediaPlanMappingService.PlaylistDiff> assigneMediaPlanOnPlaylistsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                               @ApiParam(value = "mediaPlanId", required = true) @PathVariable("mediaPlanId") Long mediaPlanId,
                                                                                                               @ApiParam(value = "advertismentId", required = true) @PathVariable("advertisementId") Long advertismentId) {
        return Optional.ofNullable(traPlaylistMediaPlanMappingService.mapMediaPlanEntriesToPlaylistWithSelectedAdvertisment(mediaPlanId, advertismentId, networkShortcut, channelShortcut))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
