package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfDiscountPT;
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
public interface ApiConfigurationTrafficDictionaryDiscount {

    @ApiOperation(value = "getAllDiscount", notes = "", response = ConfDiscountPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfDiscountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfDiscountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfDiscountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfDiscountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfDiscountPT>> getAllDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getDiscount", notes = "", response = ConfDiscountPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfDiscountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfDiscountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfDiscountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfDiscountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfDiscountPT> getDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateDiscount", notes = "", response = ConfDiscountPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfDiscountPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfDiscountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfDiscountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfDiscountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfDiscountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfDiscountPT> updateDiscountUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "discountPT", required = true) @RequestBody ConfDiscountPT discountPT);


    @ApiOperation(value = "createDiscount", notes = "", response = ConfTaxPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfDiscountPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfDiscountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfDiscountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfDiscountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfDiscountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfDiscountPT> createDiscountUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "discountPT", required = true) @RequestBody ConfDiscountPT discountPT);


    @ApiOperation(value = "deleteDiscount", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDiscountUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
