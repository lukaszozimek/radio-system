package io.protone.web.api.scheduler.impl;

import io.protone.web.api.scheduler.SchEventResource;
import io.protone.web.rest.dto.scheduler.SchEventDTO;
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
public class SchEventResourceImpl implements SchEventResource {


    @Override
    public ResponseEntity<SchEventDTO> getAllSchedulerTemplatesForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> creatSchedulerTemplatesForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETEUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETEUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> getSchedulerTemplateForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {

        return null;
    }

    @Override
    public ResponseEntity<SchEventDTO> updateSchedulerTemplatesForChannelUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO) {
        return null;
    }
}
