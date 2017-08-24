package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.api.dto.LibArtistDTO;
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
public interface LibArtistResource {

    @ApiOperation(value = "updateArtistWithOutImage", notes = "", response = LibArtistDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibArtistDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibArtistDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibArtistDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibArtistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/artist",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibArtistDTO> updateArtistWithOutImageUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibArtistDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateArtistWithImage", notes = "", response = LibArtistDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibArtistDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibArtistDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibArtistDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibArtistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/artist/{id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibArtistDTO> updateArtistWithImageUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id,
                                                                @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibArtistDTO libraryDTO,
                                                                @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover

    ) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllArtists", notes = "", response = LibArtistDTO.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibArtistDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibArtistDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibArtistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/artist",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibArtistDTO>> getAllArtistsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createArtist", notes = "", response = LibArtistDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibArtistDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibArtistDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibArtistDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibArtistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/artist",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibArtistDTO> createArtistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibArtistDTO libraryDTO,
                                                       @ApiParam(value = "cover") @RequestPart("cover") MultipartFile cover
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteArtist", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/artist/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteArtistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getArtist", notes = "", response = LibArtistDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibArtistDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibArtistDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibArtistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/artist/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibArtistDTO> getArtistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

}
