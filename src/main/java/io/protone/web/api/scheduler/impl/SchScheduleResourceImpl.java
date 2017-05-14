package io.protone.web.api.scheduler.impl;

import io.protone.web.api.scheduler.SchPlaylistResource;
import io.protone.web.api.scheduler.SchScheduleResource;
import io.protone.web.rest.dto.scheduler.SchEmissionDTO;
import io.protone.web.rest.dto.scheduler.SchPlaylistDTO;
import io.protone.web.rest.dto.scheduler.SchScheduleDTO;
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
public class SchScheduleResourceImpl implements SchScheduleResource {


    @Override
    public ResponseEntity<SchScheduleDTO> getAllSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> creatSchedulerScheduleForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETEUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> getSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> updateSchedulerScheduleForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) {
        return null;
    }
}
