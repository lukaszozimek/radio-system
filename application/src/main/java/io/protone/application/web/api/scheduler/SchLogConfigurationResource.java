package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchLogConfigurationResource {

    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchScheduleThinDTO>> getAllSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchScheduleDTO> creatSchedulerScheduleForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException;

    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "id", required = true) @PathVariable("id") LocalDate date);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchScheduleDTO> getSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "id", required = true) @PathVariable("id") LocalDate date);

    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchScheduleDTO> updateSchedulerScheduleForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException;


}