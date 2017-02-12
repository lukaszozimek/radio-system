package io.protone.custom.web.rest.network.configuration.library;

import io.protone.custom.service.dto.ConfLibraryProcessingConfigurationPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationLibraryProcessing {


    @ApiOperation(value = "updateLibraryProcessingConfiguration", notes = "", response = ConfLibraryProcessingConfigurationPT.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLibraryProcessingConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfLibraryProcessingConfigurationPT> updateLibraryProcessingConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                                      @ApiParam(value = "library", required = true) @RequestBody ConfLibraryProcessingConfigurationPT library);


    @ApiOperation(value = "createLibraryProcessingConfiguration", notes = "", response = ConfLibraryProcessingConfigurationPT.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLibraryProcessingConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLibraryProcessingConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfLibraryProcessingConfigurationPT> createLibraryProcessingConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                                       @ApiParam(value = "library", required = true) @RequestBody ConfLibraryProcessingConfigurationPT library);


    @ApiOperation(value = "deleteLibraryProcessingConfiguration", notes = "", response = Void.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryProcessingConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getLibraryProcessingConfiguration", notes = "", response = Object.class, tags={ "LIBRARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Object.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Object.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Object.class),
        @ApiResponse(code = 404, message = "Not Found", response = Object.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/{shortName}/processing",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Object> getLibraryProcessingConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);



}
