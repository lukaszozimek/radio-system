package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.LibraryPT;
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
public interface ApiChannelLibrary {


    @ApiOperation(value = "getAllLibrariesForChannel", notes = "", response = LibraryPT.class, responseContainer = "List", tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<LibraryPT>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "updateLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<LibraryPT> updateLibraryForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                              @ApiParam(value = "library", required = true) @RequestBody LibraryPT library);


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<LibraryPT> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "library", required = true) @RequestBody LibraryPT library);


    @ApiOperation(value = "deleteLibraryForChannel", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibraryForChannel", notes = "", response = LibraryPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibraryPT> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);



}
