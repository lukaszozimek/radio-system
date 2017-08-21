package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchEventResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.mapper.SchEventMapper;
import io.protone.scheduler.service.SchEventService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchEventResourceImpl implements SchEventResource {

    private final Logger log = LoggerFactory.getLogger(SchEventResourceImpl.class);

    @Inject
    private SchEventService eventService;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorChannelService channelService;

    @Inject
    private SchEventMapper schEventMapper;

    @Override
    public ResponseEntity<List<SchEventDTO>> getAllSchedulerEventsForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> createSchedulerEventForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to create SchEvent : {}, for Network: {}", schEventDTO, networkShortcut);

        if (schEventDTO.getId() != null)
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchEvent", "idexists", "A new SchEvent cannot already have an ID")).body(null);

        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CorChannel corChannel = channelService.findChannel(networkShortcut, channelShortcut);
        SchEvent schEvent = schEventMapper.toEntity(schEventDTO, corNetwork, corChannel);
        SchEvent entity = eventService.saveEvent(schEvent);
        SchEventDTO response = schEventMapper.toDto(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/scheduler/event/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {

        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) {
        return null;
    }
}
