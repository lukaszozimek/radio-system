package io.protone.custom.web.rest.network.configuration.scheduler;

import io.protone.custom.service.dto.ConfMusicLogPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationSchedulerLogMusic {


    @ApiOperation(value = "updateMusicLogConfiguration", notes = "", response = ConfMusicLogPT.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMusicLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMusicLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMusicLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMusicLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMusicLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfMusicLogPT> updateMusicLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT);


    @ApiOperation(value = "createMusicLogConfiguration", notes = "", response = ConfMusicLogPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMusicLogPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMusicLogPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMusicLogPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMusicLogPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMusicLogPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfMusicLogPT> createMusicLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT);


    @ApiOperation(value = "deleteMusicLogConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION","DICTIONARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMusicLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getMusicLogConfiguration", notes = "", response = Object.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/log/music",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);



}