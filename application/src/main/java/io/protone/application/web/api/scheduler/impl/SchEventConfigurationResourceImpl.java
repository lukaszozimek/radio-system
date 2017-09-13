package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchEventConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchEventConfigurationThinDTO;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.mapper.SchEventConfigurationMapper;
import io.protone.scheduler.service.SchEventConfigurationService;
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
public class SchEventConfigurationResourceImpl implements SchEventConfigurationResource {
    private final Logger log = LoggerFactory.getLogger(SchEventConfigurationResourceImpl.class);

    @Inject
    private SchEventConfigurationService schEventConfigurationService;

    @Inject
    private SchEventConfigurationMapper schEventConfigurationMapper;
    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchEventConfigurationThinDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchEventConfiguration, for Channel {}, Network: {}", channelShortcut, networkShortcut);

        Slice<SchEventConfiguration> entity = schEventConfigurationService.findSchEventConfigurationsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchEventConfigurationThinDTO> response = schEventConfigurationMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventConfigurationDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                               @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventConfigurationDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventConfigurationDTO SchEventConfigurationDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, networkShortcut);
        if (schEventDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchEventConfiguration", "idexists", "A new SchEventConfiguration cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchEventConfiguration traOrder = schEventConfigurationMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEventConfiguration entity = schEventConfigurationService.saveEventConfiguration(traOrder);
        SchEventConfigurationDTO response = schEventConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/event/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchEventConfiguration : {}, for Network: {}", shortName, networkShortcut);
        schEventConfigurationService.deleteSchEventConfigurationByNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok()     .build();
    }

    @Override
    public ResponseEntity<SchEventConfigurationDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchEventConfiguration : {}, for Network: {}", shortName, networkShortcut);

        SchEventConfiguration entity = schEventConfigurationService.findSchEventConfigurationsForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        SchEventConfigurationDTO response = schEventConfigurationMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventConfigurationDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventConfigurationDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventConfigurationDTO SchEventConfigurationDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, networkShortcut);
        if (schEventDTO.getId() == null) {
            return creatSchedulerTemplatesForChannelUsingPOST(networkShortcut, channelShortcut, schEventDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchEventConfiguration schEventConfiguration = schEventConfigurationMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEventConfiguration entity = schEventConfigurationService.saveEventConfiguration(schEventConfiguration);
        SchEventConfigurationDTO response = schEventConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }
}
