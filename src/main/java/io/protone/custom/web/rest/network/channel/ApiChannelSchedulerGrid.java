package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchGridPT;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerGrid {


    @ApiOperation(value = "getAllSchedulerGridForChannel", notes = "", response = SchGridPT.class, responseContainer = "List", tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchGridPT>> getAllSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "updateSchedulerGridForChannel", notes = "", response = SchGridPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchGridPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchGridPT> updateSchedulerGridForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerGridForChannel", notes = "", response = SchGridPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchGridPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchGridPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchGridPT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerGridForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid/{shortName}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getSchedulerGridForChannel", notes = "", response = SchPlaylistPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchGridPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchGridPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchGridPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchGridPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/grid/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchGridPT> getSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
