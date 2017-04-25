package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.CoreKeyPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiPropertyKey {

    @ApiOperation(value = "getPropertyKey", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") String id);


    @ApiOperation(value = "deletePropertyKey", notes = "", response = Void.class, tags = {"CONFIGURATION", "DICTIONARY",})
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


    @ApiOperation(value = "getAllPropertyKeys", notes = "", response = CoreKeyPT.class, responseContainer = "List", tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "updatePropertyKey", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO);


    @ApiOperation(value = "createPropertyKey", notes = "", response = CoreKeyPT.class, tags = {"CONFIGURATION", "DICTIONARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreKeyPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreKeyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreKeyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreKeyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreKeyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO);


}
