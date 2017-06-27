package io.protone.application.web.api.cor;

import io.protone.web.rest.dto.cor.CorKeyDTO;
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
public interface CorPropertyKeyResource {

    @ApiOperation(value = "getPropertyKey", notes = "", response = CorKeyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorKeyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorKeyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorKeyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorKeyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorKeyDTO> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") String id);


    @ApiOperation(value = "deletePropertyKey", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") String id);


    @ApiOperation(value = "getAllPropertyKeys", notes = "", response = CorKeyDTO.class, responseContainer = "List", tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorKeyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorKeyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorKeyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorKeyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorKeyDTO>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updatePropertyKey", notes = "", response = CorKeyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorKeyDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorKeyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorKeyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorKeyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorKeyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorKeyDTO> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException;


    @ApiOperation(value = "createPropertyKey", notes = "", response = CorKeyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorKeyDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorKeyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorKeyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorKeyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorKeyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorKeyDTO> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException;


}
