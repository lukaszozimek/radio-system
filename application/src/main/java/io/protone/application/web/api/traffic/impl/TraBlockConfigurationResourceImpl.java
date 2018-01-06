package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraBlockConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraBlockConfigurationDTO;
import io.protone.traffic.domain.TraBlockConfiguration;
import io.protone.traffic.mapper.TraBlockConfigurationMapper;
import io.protone.traffic.service.TraBlockConfigurationService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TraBlockConfigurationResourceImpl implements TraBlockConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(TraPlaylistResourceImpl.class);

    @Inject
    private TraBlockConfigurationService traBlockConfigurationService;

    @Inject
    private TraBlockConfigurationMapper traBlockConfigurationMapper;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> creatTrafficBlockConfigurationUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @Valid @RequestBody TraBlockConfigurationDTO traBlockConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraOrder : {}, for Network: {}", traBlockConfigurationDTO, organizationShortcut);
        if (traBlockConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrder", "idexists", "A new TraBlockConfiguration cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraBlockConfiguration traBlockConfiguration = traBlockConfigurationMapper.DTO2DB(traBlockConfigurationDTO, corChannel);
        TraBlockConfiguration entity = traBlockConfigurationService.saveBlockConfiguration(traBlockConfiguration);
        TraBlockConfigurationDTO response = traBlockConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/organization/" + channelShortcut + "/traffic/block/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<TraBlockConfigurationDTO>> getAllTrafficBlockConfigurationUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get all TraBlockConfiguration, for Channel {},  Network: {}", channelShortcut, organizationShortcut);
        List<TraBlockConfiguration> entity = traBlockConfigurationService.getAllBlockConfigurations(organizationShortcut, channelShortcut);
        List<TraBlockConfigurationDTO> response = traBlockConfigurationMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraBlockConfigurationDTO>> getAllTrafficBlockConfigurationByDateUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                        @ApiParam(value = "day", required = true) @PathVariable("day") CorDayOfWeekEnum day) {
        log.debug("REST request to get all TraBlockConfiguration, for Channel {},  Network: {}", channelShortcut, organizationShortcut);
        List<TraBlockConfiguration> entity = traBlockConfigurationService.getAllBlockConfigurationsByDay(organizationShortcut, channelShortcut, day);
        List<TraBlockConfigurationDTO> response = traBlockConfigurationMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> getTrafficBlockConfigurationUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraBlockConfiguration : {}, for Channel {} Network: {}", id, channelShortcut, organizationShortcut);
        TraBlockConfiguration entity = traBlockConfigurationService.findConfigurationBlock(id, organizationShortcut, channelShortcut);
        TraBlockConfigurationDTO response = traBlockConfigurationMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> updateTrafficBlockConfigurationUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @Valid @RequestBody TraBlockConfigurationDTO traBlockConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update TraBlockConfiguration : {}, for Channel {} Network: {}", traBlockConfigurationDTO, channelShortcut, organizationShortcut);

        if (traBlockConfigurationDTO.getId() == null) {
            return creatTrafficBlockConfigurationUsingPOST(organizationShortcut, channelShortcut, traBlockConfigurationDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraBlockConfiguration traBlockConfiguration = traBlockConfigurationMapper.DTO2DB(traBlockConfigurationDTO, corChannel);
        TraBlockConfiguration entity = traBlockConfigurationService.saveBlockConfiguration(traBlockConfiguration);
        TraBlockConfigurationDTO response = traBlockConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteTrafficBlockConfigurationUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraBlockConfiguration : {}, for Channel {} Network: {}", id, channelShortcut, organizationShortcut);
        traBlockConfigurationService.deleteBlockConfiguration(id, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traBlockConfigurationDTO", id.toString())).build();

    }
}
