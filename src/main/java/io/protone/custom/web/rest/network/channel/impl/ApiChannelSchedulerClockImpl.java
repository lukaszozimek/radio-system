package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchClockPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerClock;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ApiChannelSchedulerClockImpl implements ApiChannelSchedulerClock {

    @Override
    public ResponseEntity<List<SchClockPT>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> updateSchedulerClockForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchClockPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchClockPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
