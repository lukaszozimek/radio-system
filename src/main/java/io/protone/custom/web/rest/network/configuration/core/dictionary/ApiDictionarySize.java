package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.custom.service.dto.CoreSizePT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiDictionarySize {


    @ApiOperation(value = "createSize", notes = "", response = CoreSizePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/size",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreSizePT> createSizeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO);


    @ApiOperation(value = "deleteSize", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/size/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSizeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllSize", notes = "", response = CoreSizePT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/size",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreSizePT>> getAllSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getSize", notes = "", response = CoreSizePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/size/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreSizePT> getSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateSize", notes = "", response = CoreSizePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreSizePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreSizePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreSizePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreSizePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreSizePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/size",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreSizePT> updateSizeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO);


}
