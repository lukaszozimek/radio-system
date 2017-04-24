package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.service.dto.SchSchedulePT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerSchedule;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiChannelSchedulerScheduleImpl implements ApiChannelSchedulerSchedule {


    @Override
    public ResponseEntity<List<SchSchedulePT>> getAllSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> updateSchedulerScheduleForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchSchedulePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> creatSchedulerScheduleForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchSchedulePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> getSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }
}
