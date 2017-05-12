package io.protone.custom.web.rest.network.channel;

import io.protone.web.rest.dto.library.LibLibraryDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiChannelLibrary {

    @ApiOperation(value = "getAllLibrariesForChannel", notes = "", response = LibLibraryDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<LibLibraryDTO>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateLibraryForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<LibLibraryDTO> updateLibraryForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "library", required = true) @RequestBody LibLibraryDTO library);


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<LibLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "library", required = true) @RequestBody LibLibraryDTO library);


    @ApiOperation(value = "deleteLibraryForChannel", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibraryForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibLibraryDTO> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


}
