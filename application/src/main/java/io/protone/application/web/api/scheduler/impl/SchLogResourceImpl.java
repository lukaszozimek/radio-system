package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchLogResource;
import io.protone.scheduler.api.dto.thin.SchLogThinDTO;
import io.protone.scheduler.domain.SchLog;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchLogResourceImpl implements SchLogResource {

    public ResponseEntity<List<SchLogThinDTO>> getAllSchedulerLogForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }


    public ResponseEntity<List<SchLog>> creatSchedulerLogForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                             @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws URISyntaxException {
        return null;
    }

    public ResponseEntity<SchLog> updateSchedulerLogForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                       @ApiParam(value = "schLog", required = true) @Valid @RequestBody SchLog schLog) throws URISyntaxException {
        return null;
    }

    public ResponseEntity<Void> deleteSchedulerLogForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        return null;
    }
}
