package io.protone.web.api.traffic;

import io.protone.web.rest.dto.scheduler.SchScheduleDTO;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@Api(value = "protone", description = "Protone backend API documentation")
public interface TraPlaylistResource {
    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = TraPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraPlaylistDTO> creatChannelTrafficPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO);


    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}",
        produces = {"*/*"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelTrafficPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = TraPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraPlaylistDTO> getChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = TraPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}/download",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraPlaylistDTO> getDownloadChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = TraPlaylistDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraPlaylistDTO> updateChannelTrafficPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO);

}
