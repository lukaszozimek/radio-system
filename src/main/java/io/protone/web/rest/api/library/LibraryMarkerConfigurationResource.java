package io.protone.web.rest.api.library;

import io.protone.web.rest.dto.library.LibMarkerConfigurationDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface LibraryMarkerConfigurationResource {


    @ApiOperation(value = "updateMarkerConfiguration", notes = "", response = LibMarkerConfigurationDTO.class, tags = {"CONFIGURATION", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMarkerConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/library/marker",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<LibMarkerConfigurationDTO> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "markerConfigurationDTO", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException;


    @ApiOperation(value = "createMarkerConfiguration", notes = "", response = LibMarkerConfigurationDTO.class, tags = {"CONFIGURATION", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMarkerConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/library/marker",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<LibMarkerConfigurationDTO> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "markerConfigurationPT", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException;


    @ApiOperation(value = "deleteMarkerConfiguration", notes = "", response = Void.class, tags = {"CONFIGURATION", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/library/marker/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") String id);


    @ApiOperation(value = "getAllMarkerConfiguration", notes = "", response = LibMarkerConfigurationDTO.class, responseContainer = "List", tags = {"CONFIGURATION", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMarkerConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/library/marker",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<LibMarkerConfigurationDTO>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getMarkerConfiguration", notes = "", response = LibMarkerConfigurationDTO.class, tags = {"CONFIGURATION", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMarkerConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMarkerConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/library/marker/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibMarkerConfigurationDTO> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "id", required = true) @PathVariable("id") String id);


}
