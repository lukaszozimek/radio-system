package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.CorTagDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiDictionaryTag {

    @ApiOperation(value = "updateTag", notes = "", response = CorTagDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTagDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorTagDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTagDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTagDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTagDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorTagDTO> updateTagUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "tagDTO", required = true) @RequestBody CorTagDTO tagDTO);
    @ApiOperation(value = "createTag", notes = "", response = CorTagDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTagDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorTagDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTagDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTagDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTagDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorTagDTO> createTagUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "tagDTO", required = true) @RequestBody CorTagDTO tagDTO);


    @ApiOperation(value = "getAllTags", notes = "", response = CorTagDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTagDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTagDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTagDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTagDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorTagDTO>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "pagable", required = true) Pageable pagable);



}
