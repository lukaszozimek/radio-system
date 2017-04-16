package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchClockPT;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerClock {


    @ApiOperation(value = "getAllSchedulerClockForChannel", notes = "", response = SchClockPT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchClockPT>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "updateSchedulerClockForChannel", notes = "", response = SchClockPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchClockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchClockPT> updateSchedulerClockForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchClockPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerClockForChannel", notes = "", response = SchClockPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchClockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchClockPT> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchClockPT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerClockForChannel", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "getSchedulerClockForChannel", notes = "", response = SchClockPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchClockPT> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

}
