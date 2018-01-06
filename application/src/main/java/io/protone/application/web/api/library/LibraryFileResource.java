package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface LibraryFileResource {

    @ApiOperation(value = "getAllLibrariesForChannel", notes = "", response = LibFileLibraryDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibFileLibraryDTO>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateLibraryForChannel", notes = "", response = LibFileLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibFileLibraryDTO> updateLibraryForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "library", required = true) @Valid @RequestBody LibFileLibraryDTO library) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibFileLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibFileLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "library", required = true) @Valid @RequestBody LibFileLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteLibraryForChannel", notes = "", tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibraryForChannel", notes = "", response = LibFileLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibFileLibraryDTO> getLibraryForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


}
