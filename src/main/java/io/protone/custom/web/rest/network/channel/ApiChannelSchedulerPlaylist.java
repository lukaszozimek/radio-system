package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerPlaylist {


    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, responseContainer = "List", tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable);


    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchPlaylistPT> updateSchedulerPlaylisForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchPlaylistPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchPlaylistPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchPlaylistPT> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date,
                                                                         @ApiParam(value = "random", required = false) @RequestParam(value = "random", required = false) Boolean random);


}
