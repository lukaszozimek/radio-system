package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfInvoiceStatusPT;
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
public interface ApiConfigurationTrafficDictionaryInvoiceStatus {

    @ApiOperation(value = "getAllInvoiceStatus", notes = "", response = ConfInvoiceStatusPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfInvoiceStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/invoice/status",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfInvoiceStatusPT>> getAllInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getInvoiceStatus", notes = "", response = ConfInvoiceStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OKv", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfInvoiceStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/invoice/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfInvoiceStatusPT> getInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateInvoiceStatus", notes = "", response = ConfInvoiceStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfInvoiceStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/invoice/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfInvoiceStatusPT> updateInvoiceStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT taxDTO);


    @ApiOperation(value = "createInvoiceStatus", notes = "", response = ConfInvoiceStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfInvoiceStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfInvoiceStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/invoice/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfInvoiceStatusPT> createInvoiceStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT taxDTO);


    @ApiOperation(value = "deleteInvoiceStatus", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/invoice/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInvoiceStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
