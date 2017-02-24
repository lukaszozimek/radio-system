package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.ConfPersonPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiDictionaryPeople {

    @ApiOperation(value = "updatePerson", notes = "", response = ConfPersonPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfPersonPT> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO);

    @ApiOperation(value = "createPersonEntity", notes = "", response = ConfPersonPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfPersonPT> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "personDTO", required = true) @RequestBody ConfPersonPT personDTO);


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/people/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllPeople", notes = "", response = ConfPersonPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfPersonPT>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getPerson", notes = "", response = ConfPersonPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfPersonPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfPersonPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfPersonPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfPersonPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/network/dictionary/people/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfPersonPT> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}