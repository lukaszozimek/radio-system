package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchLogDTO;
import io.protone.scheduler.api.dto.thin.SchLogThinDTO;
import io.protone.scheduler.domain.SchLog;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchLogResource {

    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchLogDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchLogThinDTO>> getAllSchedulerLogForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchLogDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchLogDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<List<SchLog>> creatSchedulerLogForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                 @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws URISyntaxException;

    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchLogDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchLogDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchLog> updateSchedulerLogForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                           @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                           @ApiParam(value = "schLog", required = true) @Valid @RequestBody SchLog schLog) throws URISyntaxException;

    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}/{date}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerLogForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                            @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date);

}
