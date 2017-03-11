package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfTaxPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationTrafficDictionaryOrderStatus {

    @ApiOperation(value = "getAllTaxes", notes = "", response = ConfTaxPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTaxPT>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getTax", notes = "", response = ConfTaxPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfTaxPT> getTaxUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTax", notes = "", response = ConfTaxPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfTaxPT> updateTaxUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO);


    @ApiOperation(value = "createTax", notes = "", response = ConfTaxPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTaxPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTaxPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTaxPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTaxPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTaxPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfTaxPT> createTaxUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO);


    @ApiOperation(value = "deleteTax", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
