package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchEventPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerEvent {


    @ApiOperation(value = "getAllSchedulerTemplatesForChannel", notes = "", response = SchEventPT.class, responseContainer = "List", tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchEventPT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "updateSchedulerTemplatesForChannel", notes = "", response = SchEventPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchEventPT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "event", required = true) @RequestBody SchEventPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchEventPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchEventPT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "event", required = true) @RequestBody SchEventPT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerTemplateForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getSchedulerTemplateForChannel", notes = "", response = SchEventPT.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchEventPT> getSchedulerTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);
}
