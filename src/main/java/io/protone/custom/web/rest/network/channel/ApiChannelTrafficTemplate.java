package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.SchTemplatePT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelTrafficTemplate {


    @ApiOperation(value = "getAllTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);

    @ApiOperation(value = "updateTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateTrafficTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createTrafficTemplatesForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatTrafficTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "deleteTrafficTemplateForChannel", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getTrafficTemplateForChannel", notes = "", response = SchTemplatePT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/templates/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getTrafficTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);



}
