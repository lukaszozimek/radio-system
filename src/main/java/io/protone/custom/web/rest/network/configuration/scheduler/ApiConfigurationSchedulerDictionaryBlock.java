package io.protone.custom.web.rest.network.configuration.scheduler;

import io.protone.custom.service.dto.ConfBlockPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationSchedulerDictionaryBlock {

    @ApiOperation(value = "updateBlockTypes", notes = "", response = ConfBlockPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfBlockPT> updateBlockTypesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "confBlockPT", required = true) @RequestBody ConfBlockPT confBlockPT);


    @ApiOperation(value = "createIndustry", notes = "", response = ConfBlockPT.class, tags={ "CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfBlockPT> createBlockTypesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "confBlockPT", required = true) @RequestBody ConfBlockPT confBlockPT);


    @ApiOperation(value = "deleteBlockType", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block/{blockShortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBlockTypeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "blockShortName", required = true) @PathVariable("blockShortName") String blockShortName);


    @ApiOperation(value = "getAllIndustries", notes = "", response = ConfBlockPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfBlockPT>> getAllBlockTypesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable);


    @ApiOperation(value = "getBlockType", notes = "", response = ConfBlockPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfBlockPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfBlockPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfBlockPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfBlockPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/scheduler/dictionary/block/{blockShortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfBlockPT> getBlockTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "blockShortName", required = true) @PathVariable("blockShortName") String blockShortName);



}
