package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.ConfTagPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiDictionaryTag {

    @ApiOperation(value = "updateTag", notes = "", response = ConfTagPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfTagPT> updateTagUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO);
    @ApiOperation(value = "createTag", notes = "", response = ConfTagPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfTagPT> createTagUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO);


    @ApiOperation(value = "getAllTags", notes = "", response = ConfTagPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTagPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTagPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTagPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTagPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/tag",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTagPT>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);



}
