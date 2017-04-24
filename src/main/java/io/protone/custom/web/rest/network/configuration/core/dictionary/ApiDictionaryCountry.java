package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.ConfCountryPt;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiDictionaryCountry {


    @ApiOperation(value = "updateCountry", notes = "", response = ConfCountryPt.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfCountryPt> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO);


    @ApiOperation(value = "createCountry", notes = "", response = ConfCountryPt.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfCountryPt> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO);


    @ApiOperation(value = "deletePerson", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
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


    @ApiOperation(value = "getAllCountries", notes = "", response = ConfCountryPt.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCountryPt>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getPerson", notes = "", response = ConfCountryPt.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCountryPt.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCountryPt.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCountryPt.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCountryPt.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/country/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfCountryPt> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
