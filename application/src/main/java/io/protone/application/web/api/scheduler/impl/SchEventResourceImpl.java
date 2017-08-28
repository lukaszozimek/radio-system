package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchEventResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.thin.SchEventThinDTO;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.mapper.SchEventMapper;
import io.protone.scheduler.service.SchEventService;
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
public class SchEventResourceImpl implements SchEventResource {
    private final Logger log = LoggerFactory.getLogger(SchEventResourceImpl.class);

    @Inject
    private SchEventService schEventService;

    @Inject
    private SchEventMapper schEventMapper;
    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchEventThinDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchEvent, for Channel {}, Network: {}", channelShortcut, networkShortcut);

        Slice<SchEvent> entity = schEventService.findSchEventsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchEventThinDTO> response = schEventMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventDTO SchEventDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, networkShortcut);
        if (schEventDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchEvent", "idexists", "A new SchEvent cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchEvent traOrder = schEventMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEvent entity = schEventService.saveEvent(traOrder);
        SchEventDTO response = schEventMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/event/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchEvent : {}, for Network: {}", shortName, networkShortcut);
        schEventService.deleteSchEventByNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<SchEventDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchEvent : {}, for Network: {}", shortName, networkShortcut);

        SchEvent entity = schEventService.findSchEventsForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        SchEventDTO response = schEventMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventDTO SchEventDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, networkShortcut);
        if (schEventDTO.getId() == null) {
            return creatSchedulerTemplatesForChannelUsingPOST(networkShortcut, channelShortcut, schEventDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchEvent traOrder = schEventMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEvent entity = schEventService.saveEvent(traOrder);
        SchEventDTO response = schEventMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }
}
