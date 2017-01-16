package io.protone.custom.web.rest.network.configuration.library;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationLibraryMarker {


    @ApiOperation(value = "updateMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfMarkerConfigurationPT> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "markerConfigurationDTO" ,required=true ) @RequestBody ConfMarkerConfigurationPT markerConfigurationDTO);


    @ApiOperation(value = "createMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfMarkerConfigurationPT> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "markerConfigurationPT" ,required=true ) @RequestBody ConfMarkerConfigurationPT markerConfigurationPT);


    @ApiOperation(value = "deleteMarkerConfiguration", notes = "", response = Void.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker/{id}",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id",required=true ) @PathVariable("id") String id);


    @ApiOperation(value = "getAllMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, responseContainer = "List", tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfMarkerConfigurationPT>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getMarkerConfiguration", notes = "", response = ConfMarkerConfigurationPT.class, tags={ "CONFIGURATION","CORE", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfMarkerConfigurationPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfMarkerConfigurationPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/library/marker/{id}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfMarkerConfigurationPT> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "id",required=true ) @PathVariable("id") String id);



}
