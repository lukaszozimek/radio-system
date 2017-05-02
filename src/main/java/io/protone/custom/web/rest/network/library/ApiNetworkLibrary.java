package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.dto.LibraryPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkLibrary {

    @ApiOperation(value = "updateLibrary", notes = "", response = LibraryPT.class, tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<LibraryPT> updateLibraryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "library", required = true) @Valid @RequestBody LibraryPT library);


    @ApiOperation(value = "getAllLibraries", notes = "", response = LibraryPT.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<LibraryPT>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createLibrary", notes = "", response = LibraryPT.class, tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<LibraryPT> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "library", required = true) @Valid @RequestBody LibraryPT library);


    @ApiOperation(value = "deleteLibrary", notes = "", response = Void.class, tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibrary", notes = "", response = LibraryPT.class, tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibraryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibraryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibraryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibraryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibraryPT> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


}
