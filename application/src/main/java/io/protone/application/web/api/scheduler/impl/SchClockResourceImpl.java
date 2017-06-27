package io.protone.application.web.api.scheduler.impl;

import io.protone.web.api.scheduler.SchClockResource;
import io.protone.web.rest.dto.scheduler.SchClockDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchClockResourceImpl implements SchClockResource {


    @Override
    public ResponseEntity<SchClockDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<SchClockDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchClockDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

}
