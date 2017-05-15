package io.protone.web.api.scheduler.impl;

import io.protone.web.api.scheduler.SchPlaylistResource;
import io.protone.web.rest.dto.scheduler.SchEmissionDTO;
import io.protone.web.rest.dto.scheduler.SchPlaylistDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchPlaylistResourceImpl implements SchPlaylistResource {

    @Override
    public ResponseEntity<SchPlaylistDTO> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
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
