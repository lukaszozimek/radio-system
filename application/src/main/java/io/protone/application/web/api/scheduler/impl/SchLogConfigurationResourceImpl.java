package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchLogConfigurationResource;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

public class SchLogConfigurationResourceImpl implements SchLogConfigurationResource{


    @Override
    public ResponseEntity<List<SchScheduleThinDTO>> getAllSchedulerScheduleForChannelUsingGET(String networkShortcut, String channelShortcut, Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> creatSchedulerScheduleForChannelUsingPOST(String networkShortcut, String channelShortcut, SchScheduleDTO schScheduleDTO) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(String networkShortcut, String channelShortcut, LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> getSchedulerScheduleForChannelUsingGET(String networkShortcut, String channelShortcut, LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> updateSchedulerScheduleForChannelUsingPUT(String networkShortcut, String channelShortcut, SchScheduleDTO schScheduleDTO) throws URISyntaxException {
        return null;
    }
}
