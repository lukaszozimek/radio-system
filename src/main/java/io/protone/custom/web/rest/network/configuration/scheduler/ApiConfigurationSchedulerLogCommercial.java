package io.protone.custom.web.rest.network.configuration.scheduler;

import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationSchedulerLogCommercial {


    @ApiOperation(value = "updateCommercialLogConfiguration", notes = "", response = ConfCommercialLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCommercialLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT);


    @ApiOperation(value = "createCommercialLogConfiguration", notes = "", response = ConfCommercialLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCommercialLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCommercialLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT);


    @ApiOperation(value = "deleteCommercialLogConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getCommercialLogConfiguration", notes = "", response = Object.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/commercial",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);



}
