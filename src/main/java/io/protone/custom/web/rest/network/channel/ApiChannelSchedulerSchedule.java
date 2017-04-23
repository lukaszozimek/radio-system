package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.service.dto.SchSchedulePT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerSchedule {


    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchSchedulePT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchSchedulePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchSchedulePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchSchedulePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchSchedulePT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/schedule",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchSchedulePT>> getAllSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchSchedulePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchSchedulePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchSchedulePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchSchedulePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchSchedulePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchSchedulePT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/schedule",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchSchedulePT> updateSchedulerScheduleForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchSchedulePT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchSchedulePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchSchedulePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchSchedulePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchSchedulePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchSchedulePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchSchedulePT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/schedule",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchSchedulePT> creatSchedulerScheduleForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchSchedulePT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchSchedulePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchSchedulePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchSchedulePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchSchedulePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchSchedulePT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchSchedulePT> getSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") String date);

}
