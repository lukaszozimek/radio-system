package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchSchedulePT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerSchedule;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiChannelSchedulerScheduleImpl implements ApiChannelSchedulerSchedule {


    @Override
    public ResponseEntity<List<SchSchedulePT>> getAllSchedulerScheduleForChannelUsingGET(String networkShortcut, String channelShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> updateSchedulerScheduleForChanneltUsingPUT(String networkShortcut, String channelShortcut, SchSchedulePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> creatSchedulerScheduleForChannelUsingPOST(String networkShortcut, String channelShortcut, SchSchedulePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(String networkShortcut, String channelShortcut, String date) {
        return null;
    }

    @Override
    public ResponseEntity<SchSchedulePT> getSchedulerScheduleForChannelUsingGET(String networkShortcut, String channelShortcut, String date) {
        return null;
    }
}
