package io.protone.web.api.scheduler.impl;

import io.protone.web.api.scheduler.SchEventResource;
import io.protone.web.api.scheduler.SchGridResource;
import io.protone.web.rest.dto.scheduler.SchEventDTO;
import io.protone.web.rest.dto.scheduler.SchGridDTO;
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
public class SchGridResourceImpl implements SchGridResource {


    @Override
    public ResponseEntity<SchGridDTO> getSchedulerGridForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridDTO> getAllSchedulerGridForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridDTO> creatSchedulerPlaylistForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETEUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridDTO> updateSchedulerGridForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) {
        return null;
    }
}
