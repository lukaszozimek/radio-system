package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchClockConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchClockConfigurationThinDTO;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.mapper.SchClockConfigurationMapper;
import io.protone.scheduler.service.SchClockConfigurationService;
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
public class SchClockConfigurationResourceImpl implements SchClockConfigurationResource {
    private final Logger log = LoggerFactory.getLogger(SchClockConfigurationResourceImpl.class);


    @Inject
    private SchClockConfigurationService schClockConfigurationService;

    @Inject
    private SchClockConfigurationMapper schClockConfigurationMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<SchClockConfigurationDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockConfigurationDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to savSchClockConfigurationDTO SchClockConfigurationDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);
        if (clockDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchClockConfiguration", "idexists", "A new SchClockConfiguration cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClockConfiguration schClock = schClockConfigurationMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClockConfiguration entity = schClockConfigurationService.saveClockConfiguration(schClock);
        SchClockConfigurationDTO response = schClockConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/clock/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<SchClockConfigurationThinDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchClockConfiguration, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchClockConfiguration> entity = schClockConfigurationService.findSchClockConfigurationsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchClockConfigurationThinDTO> response = schClockConfigurationMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockConfigurationDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchClockConfiguration : {}, for Network: {}", shortName, networkShortcut);
        SchClockConfiguration entity = schClockConfigurationService.findSchClockConfigurationForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        SchClockConfigurationDTO response = schClockConfigurationMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockConfigurationDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockConfigurationDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to saveSchClockConfigurationDTO SchClockConfigurationDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);

        if (clockDTO.getId() == null) {
            return creatSchedulerClockForChannelUsingPOST(networkShortcut, channelShortcut, clockDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClockConfiguration schClock = schClockConfigurationMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClockConfiguration entity = schClockConfigurationService.saveClockConfiguration(schClock);
        SchClockConfigurationDTO response = schClockConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchClockConfiguration : {}, for Network: {}", shortName, networkShortcut);
        schClockConfigurationService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("SchClockConfiguration", shortName.toString())).build();
    }

}
