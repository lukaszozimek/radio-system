package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.service.dto.CoreAreaPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiDictionaryArea {


    @ApiOperation(value = "getArea", notes = "", response = CoreAreaPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/area/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "deleteArea", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/area/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllArea", notes = "", response = CoreAreaPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/area",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "updateArea", notes = "", response = CoreAreaPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/area",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO);


    @ApiOperation(value = "createArea", notes = "", response = CoreAreaPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreAreaPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreAreaPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreAreaPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreAreaPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreAreaPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/area",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO);



}
