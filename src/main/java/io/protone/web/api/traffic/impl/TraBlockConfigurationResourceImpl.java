package io.protone.web.api.traffic.impl;

import io.protone.web.api.traffic.TraBlockConfigurationResource;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RestController
public class TraBlockConfigurationResourceImpl implements TraBlockConfigurationResource {
    public ResponseEntity<TraBlockConfigurationDTO> creatSchedulerScheduleForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                       @Valid @RequestBody TraBlockConfigurationDTO traBlockConfigurationDTO) {
        return null;
    }


    public ResponseEntity<TraBlockConfigurationDTO> getAllSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }


    public ResponseEntity<TraBlockConfigurationDTO> getAllSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    public ResponseEntity<TraBlockConfigurationDTO> updateSchedulerScheduleForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }


}
