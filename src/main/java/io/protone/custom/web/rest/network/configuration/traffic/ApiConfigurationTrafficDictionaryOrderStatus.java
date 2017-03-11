package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
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

    @ApiOperation(value = "getAllTaxes", notes = "", response = ConfTraOrderStatusPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraOrderStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/order/status",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTraOrderStatusPT>> getAllOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getTax", notes = "", response = ConfTraOrderStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraOrderStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/order/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfTraOrderStatusPT> getOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTax", notes = "", response = ConfTraOrderStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraOrderStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/order/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfTraOrderStatusPT> updateOrderStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT taxDTO);


    @ApiOperation(value = "createTax", notes = "", response = ConfTraOrderStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraOrderStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraOrderStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/order/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfTraOrderStatusPT> createOrderStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT confTraOrderStatusPT);


    @ApiOperation(value = "deleteTax", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/order/status{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrderStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
