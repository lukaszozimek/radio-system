package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.CoreRangePT;
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
public interface ApiDictionaryRange {



    @ApiOperation(value = "createRange", notes = "", response = CoreRangePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/range",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO);


    @ApiOperation(value = "getAllRange", notes = "", response = CoreRangePT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/range",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getRange", notes = "", response = CoreRangePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/range/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateRange", notes = "", response = CoreRangePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreRangePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreRangePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreRangePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreRangePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreRangePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/range",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO);


    @ApiOperation(value = "deleteRange", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/range/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
