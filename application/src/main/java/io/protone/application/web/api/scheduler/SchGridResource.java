package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.api.dto.thin.SchGridThinDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchGridResource {
    @ApiOperation(value = "getSchedulerGridForChannel", notes = "", response = SchGridDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchGridDTO> getSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getAllSchedulerGridForChannel", notes = "", response = SchGridDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchGridThinDTO>> getAllSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerGridForChannel", notes = "", response = SchGridDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchGridDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchGridDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteSchedulerGridForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "updateSchedulerGridForChannel", notes = "", response = SchGridDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchGridDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchGridDTO> updateSchedulerGridForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException;


}
