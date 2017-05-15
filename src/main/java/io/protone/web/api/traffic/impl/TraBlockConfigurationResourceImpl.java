package io.protone.web.api.traffic.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.TraOrder;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraBlockConfigurationService;
import io.protone.web.api.traffic.TraBlockConfigurationResource;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.web.rest.mapper.TraBlockConfigurationMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TraBlockConfigurationService traBlockConfigurationService;

    @Autowired
    private TraBlockConfigurationMapper traBlockConfigurationMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> creatTrafficBlockConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @Valid @RequestBody TraBlockConfigurationDTO traBlockConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save TraOrder : {}, for Network: {}", traBlockConfigurationDTO, networkShortcut);
        if (traBlockConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrder", "idexists", "A new TraOrder cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraBlockConfiguration traBlockConfiguration = traBlockConfigurationMapper.DTO2DB(traBlockConfigurationDTO, corNetwork);
        TraBlockConfiguration entity = traBlockConfigurationService.saveBlockConfiguration(traBlockConfiguration);
        TraBlockConfigurationDTO response = traBlockConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/order/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<List<TraBlockConfigurationDTO>> getAllTrafficBlockConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<List<TraBlockConfigurationDTO>> getAllTrafficBlockConfigurationByDateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                        @ApiParam(value = "day", required = true) @PathVariable("day") TraBlockConfigurationDTO.DayOfWeekEnum day) {
        return null;
    }

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> getTrafficBlockConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraBlockConfiguration : {}, for Channel {} Network: {}", id, channelShortcut, networkShortcut);
        TraBlockConfiguration entity = traBlockConfigurationService.findConfigurationBlock(id, networkShortcut);
        TraBlockConfigurationDTO response = traBlockConfigurationMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraBlockConfigurationDTO> updateTrafficBlockConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTrafficBlockConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraBlockConfiguration : {}, for Channel {} Network: {}", id, channelShortcut, networkShortcut);
        traBlockConfigurationService.deleteBlockConfiguration(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traBlockConfigurationDTO", id.toString())).build();

    }
}
