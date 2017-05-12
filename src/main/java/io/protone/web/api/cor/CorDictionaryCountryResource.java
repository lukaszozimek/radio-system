package io.protone.web.api.cor;

import io.protone.web.rest.dto.cor.CorCountryDTO;
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
public interface CorDictionaryCountryResource {


    @ApiOperation(value = "updateCountry", notes = "", response = CorCountryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCountryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorCountryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCountryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCountryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCountryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorCountryDTO> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "countryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException;


    @ApiOperation(value = "createCountry", notes = "", response = CorCountryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCountryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorCountryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCountryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCountryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCountryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorCountryDTO> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "countryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException;


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllCountries", notes = "", response = CorCountryDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCountryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCountryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCountryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCountryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorCountryDTO>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getPerson", notes = "", response = CorCountryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCountryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCountryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCountryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCountryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorCountryDTO> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
