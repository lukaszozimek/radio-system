package io.protone.application.web.api.library;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.api.dto.LibLabelDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface LibLabelResource {

    @ApiOperation(value = "updateLabelWithOutCover", notes = "", response = LibLabelDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLabelDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLabelDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLabelDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLabelDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLabelDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/library/label",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibLabelDTO> updateLabelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                    @ApiParam(value = "libraryDTO", required = true) @Valid @RequestBody LibLabelDTO libraryDTO) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllLabels", notes = "", response = LibLabelDTO.class, responseContainer = "List", tags = {"LIBRARY", "CORE",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLabelDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLabelDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLabelDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLabelDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/library/label",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibLabelDTO>> getAllLabelsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                           @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createLabel", notes = "", response = LibLabelDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLabelDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibLabelDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLabelDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLabelDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLabelDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/library/label",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibLabelDTO> createLabelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                     @ApiParam(value = "libraryDTO", required = true) @Valid @RequestPart("libraryDTO") LibLabelDTO libraryDTO
    ) throws URISyntaxException, CreateBucketException, TikaException, IOException, SAXException;


    @ApiOperation(value = "deleteLabel", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/library/label/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLabelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getLabel", notes = "", response = LibLabelDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibLabelDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibLabelDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibLabelDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibLabelDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/library/label/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibLabelDTO> getLabelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
