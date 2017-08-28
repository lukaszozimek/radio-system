package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchClockResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.thin.SchClockThinDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.mapper.SchClockMapper;
import io.protone.scheduler.service.SchClockService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchClockResourceImpl implements SchClockResource {
    private final Logger log = LoggerFactory.getLogger(SchClockResourceImpl.class);


    @Inject
    private SchClockService schClockService;

    @Inject
    private SchClockMapper schClockMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<SchClockDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to savSchClockDTO SchClockDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);
        if (clockDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchClock", "idexists", "A new SchClock cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClock schClock = schClockMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClock entity = schClockService.saveClock(schClock);
        SchClockDTO response = schClockMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/clock/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<SchClockThinDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchClock, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchClock> entity = schClockService.findSchClocksForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchClockThinDTO> response = schClockMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchClock : {}, for Network: {}", shortName, networkShortcut);
        SchClock entity = schClockService.findSchClockForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        SchClockDTO response = schClockMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to saveSchClockDTO SchClockDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);

        if (clockDTO.getId() == null) {
            return creatSchedulerClockForChannelUsingPOST(networkShortcut, channelShortcut, clockDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClock schClock = schClockMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClock entity = schClockService.saveClock(schClock);
        SchClockDTO response = schClockMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchClock : {}, for Network: {}", shortName, networkShortcut);
        schClockService.deleteSchClockByNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("SchClock", shortName.toString())).build();
    }

}
