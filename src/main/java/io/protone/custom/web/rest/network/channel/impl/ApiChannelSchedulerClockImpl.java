package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchClockPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerClock;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ApiChannelSchedulerClockImpl implements ApiChannelSchedulerClock {

    @Override
    public ResponseEntity<List<SchClockPT>> getAllSchedulerClockForChannelUsingGET(String networkShortcut, String channelShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> updateSchedulerClockForChanneltUsingPUT(String networkShortcut, String channelShortcut, SchClockPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> creatSchedulerClockForChannelUsingPOST(String networkShortcut, String channelShortcut, SchClockPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(String networkShortcut, String channelShortcut, String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockPT> getSchedulerClockForChannelUsingGET(String networkShortcut, String channelShortcut, String shortName) {
        return null;
    }
}
