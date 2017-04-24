package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfCurrencyPT;
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
public interface ApiConfigurationTrafficDictionaryCurrency {

    @ApiOperation(value = "getAllCurrency", notes = "", response = ConfCurrencyPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},

        method = RequestMethod.GET)
    ResponseEntity<List<ConfCurrencyPT>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable);


    @ApiOperation(value = "getCurrency", notes = "", response = ConfCurrencyPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfCurrencyPT> getCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateCurrency", notes = "", response = ConfCurrencyPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfCurrencyPT> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO);

    @ApiOperation(value = "createCurrency", notes = "", response = ConfCurrencyPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCurrencyPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCurrencyPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCurrencyPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCurrencyPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCurrencyPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfCurrencyPT> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO);


    @ApiOperation(value = "deleteCurrency", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
