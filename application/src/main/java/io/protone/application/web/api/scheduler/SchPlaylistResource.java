package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchPlaylistResource {

    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchPlaylistDTO>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO);

    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String date);

    @ApiOperation(value = "deleteSchedulerPlaylistElementForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}/{seqNumber}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistElementForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                             @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchPlaylistDTO> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "date", required = true) @PathVariable("date") String date);

    @ApiOperation(value = "getSchedulerPlaylistElementForChannel", notes = "", response = SchEmissionDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEmissionDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEmissionDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEmissionDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEmissionDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}/{seqNumber}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchEmissionDTO> getSchedulerPlaylistElementForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                                 @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber);


    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchPlaylistDTO> updateSchedulerPlaylistForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO);

    @ApiOperation(value = "moveSchedulerChannelPlaylistElement", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}/{from}/move/{to}",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistDTO> moveElementInPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                  @ApiParam(value = "from", required = true) @PathVariable("from") Long from,
                                                                  @ApiParam(value = "to", required = true) @PathVariable("to") Long to);

    @ApiOperation(value = "addSchedulerChannelPlaylistElement", notes = "", response = SchPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}/add/{seqNumber}", produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistDTO> addElementInPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                 @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber, @RequestBody SchEmissionDTO emissionDTO);


}
