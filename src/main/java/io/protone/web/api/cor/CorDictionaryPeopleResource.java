package io.protone.web.api.cor;

import io.protone.web.rest.dto.cor.CorPersonDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CorDictionaryPeopleResource {

    @ApiOperation(value = "updatePerson", notes = "", response = CorPersonDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorPersonDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorPersonDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorPersonDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorPersonDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorPersonDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorPersonDTO> updatePersonUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "personDTO", required = true) @Valid @RequestBody CorPersonDTO personDTO) throws URISyntaxException;

    @ApiOperation(value = "DTO2DB", notes = "", response = CorPersonDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorPersonDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorPersonDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorPersonDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorPersonDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorPersonDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorPersonDTO> createPersonUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "personDTO", required = true) @Valid @RequestBody CorPersonDTO personDTO) throws URISyntaxException;


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/people/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePersonUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllPeople", notes = "", response = CorPersonDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorPersonDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorPersonDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorPersonDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorPersonDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/people",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorPersonDTO>> getAllPeopleUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getPerson", notes = "", response = CorPersonDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorPersonDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorPersonDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorPersonDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorPersonDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/people/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorPersonDTO> getPersonUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
