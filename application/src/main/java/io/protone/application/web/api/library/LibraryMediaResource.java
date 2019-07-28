package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibMediaLibraryDTO;
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
public interface LibraryMediaResource {

    @ApiOperation(value = "updateLibraryWithOutCover", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibMediaLibraryDTO> updateLibraryWithOutCoverUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibMediaLibraryDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateLibraryWithCover", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibMediaLibraryDTO> updateLibraryWithCoverUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                       @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                       @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover

    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllLibraries", notes = "", response = LibMediaLibraryDTO.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibMediaLibraryDTO>> getAllLibrariesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createLibrary", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibMediaLibraryDTO> createLibraryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                              @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteLibrary", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibrary", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibMediaLibraryDTO> getLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);

    @ApiOperation(value = "getAllLibrariesForChannel", notes = "", response = LibMediaLibraryDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibMediaLibraryDTO>> getAllLibrariesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateLibraryWithoutCoverForChannel", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibMediaLibraryDTO> updateLibraryWithoutCoverForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "library", required = true) @Valid @RequestBody LibMediaLibraryDTO library) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateLibraryWithCoverForChannel", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibMediaLibraryDTO> updateLibraryWithCoverForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                 @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                                 @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createLibraryForChannel", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibMediaLibraryDTO> createLibraryForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "library", required = true) @Valid @RequestPart("libraryDTO") LibMediaLibraryDTO libraryDTO,
                                                                        @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteLibraryForChannel", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLibraryForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getLibraryForChannel", notes = "", response = LibMediaLibraryDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibMediaLibraryDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibMediaLibraryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibMediaLibraryDTO> getLibraryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


}
