package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibAlbumDTO;
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
public interface LibAlbumResource {

    @ApiOperation(value = "updateAlbumWithOutCover", notes = "", response = LibAlbumDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibAlbumDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibAlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibAlbumDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibAlbumDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibAlbumDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibAlbumDTO> updateAlbumWithOutCoverUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibAlbumDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateAlbumWithCover", notes = "", response = LibAlbumDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibAlbumDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibAlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibAlbumDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibAlbumDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibAlbumDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album/{id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibAlbumDTO> updateAlbumWithCoverUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                              @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibAlbumDTO libraryDTO,
                                                              @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover

    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllAlbums", notes = "", response = LibAlbumDTO.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibAlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibAlbumDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibAlbumDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibAlbumDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibAlbumDTO>> getAllAlbumsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createAlbum", notes = "", response = LibAlbumDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibAlbumDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibAlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibAlbumDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibAlbumDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibAlbumDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibAlbumDTO> createAlbumUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibAlbumDTO libraryDTO,
                                                     @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteAlbum", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAlbumUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAlbum", notes = "", response = LibAlbumDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibAlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibAlbumDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibAlbumDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibAlbumDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/album/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibAlbumDTO> getAlbumUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

}
