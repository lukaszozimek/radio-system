package io.protone.web.api.traffic.impl;

import io.protone.web.api.traffic.TraPlaylistResource;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraPlaylistResourceImpl implements TraPlaylistResource {


    public ResponseEntity<TraPlaylistDTO> creatSchedulerScheduleForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) {
        return null;
    }


    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETEUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }


    public ResponseEntity<TraPlaylistDTO> getSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date) {

        return null;
    }


    public ResponseEntity<TraPlaylistDTO> updateSchedulerScheduleForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) {
        return null;
    }

}
