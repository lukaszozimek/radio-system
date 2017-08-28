package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchPlaylistResource;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.api.dto.thin.SchPlaylistThinDTO;
import io.protone.scheduler.mapper.SchPlaylistMapper;
import io.protone.scheduler.service.SchPlaylistService;
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
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchPlaylistResourceImpl implements SchPlaylistResource {
    private final Logger log = LoggerFactory.getLogger(SchPlaylistResourceImpl.class);
    @Inject
    private SchPlaylistService schPlaylistService;

    @Inject
    private SchPlaylistMapper schPlaylistMapper;

    @Override
    public ResponseEntity<List<SchPlaylistThinDTO>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistElementForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                                    @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<SchEmissionDTO> getSchedulerPlaylistElementForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                                        @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> updateSchedulerPlaylistForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO) {
        return null;

    }

    @Override
    public ResponseEntity<SchPlaylistDTO> moveElementInPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                         @ApiParam(value = "from", required = true) @PathVariable("from") Long from,
                                                                         @ApiParam(value = "to", required = true) @PathVariable("to") Long to) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> addElementInPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                        @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber, @RequestBody SchEmissionDTO emissionDTO) {
        return null;
    }
}
