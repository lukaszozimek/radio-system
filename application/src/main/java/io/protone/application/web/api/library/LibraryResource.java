package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibLibraryDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface LibraryResource {

    @ApiOperation(value = "updateLibraryWithOutCover", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibLibraryDTO> updateLibraryWithOutCoverUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateLibraryWithCover", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibLibraryDTO> updateLibraryWithCoverUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                  @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibLibraryDTO libraryDTO,
                                                                  @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover

    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllLibraries", notes = "", response = LibLibraryDTO.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibLibraryDTO>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createLibrary", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibLibraryDTO> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibLibraryDTO libraryDTO,
                                                         @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteLibrary", notes = "", response = Void.class, tags = {"LIBRARY",})
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


    @ApiOperation(value = "getLibrary", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibLibraryDTO> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);

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


    @ApiOperation(value = "updateLibraryWithoutCoverForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
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
    ResponseEntity<LibLibraryDTO> updateLibraryWithoutCoverForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "library", required = true) @Valid @RequestBody LibLibraryDTO library) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateLibraryWithCoverForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibLibraryDTO> updateLibraryWithCoverForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                            @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibLibraryDTO libraryDTO,
                                                                            @ApiParam(value = "cover")  @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibLibraryDTO libraryDTO,
                                                                   @ApiParam(value = "cover")  @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


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
