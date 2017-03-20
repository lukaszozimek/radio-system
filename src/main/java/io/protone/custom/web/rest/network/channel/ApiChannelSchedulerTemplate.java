package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchTemplatePT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelSchedulerTemplate {


    @ApiOperation(value = "getAllSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


    @ApiOperation(value = "updateSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "deleteSchedulerTemplateForChannel", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getSchedulerTemplateForChannel", notes = "", response = SchTemplatePT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/templates/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getSchedulerTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                         @ApiParam(value = "random", required = false) @RequestParam(value = "random", required = false) Boolean random);


}
