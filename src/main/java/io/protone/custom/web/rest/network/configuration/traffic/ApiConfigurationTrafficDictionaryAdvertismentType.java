package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfTraAdvertismentTypePT;
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
public interface ApiConfigurationTrafficDictionaryAdvertismentType {

    @ApiOperation(value = "getAllTaxes", notes = "", response = ConfTraAdvertismentTypePT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraAdvertismentTypePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/advertisment/type",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfTraAdvertismentTypePT>> getAllAdvertismentTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getTax", notes = "", response = ConfTraAdvertismentTypePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraAdvertismentTypePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/advertisment/type/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfTraAdvertismentTypePT> getAdvertismentTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTax", notes = "", response = ConfTraAdvertismentTypePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraAdvertismentTypePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/advertisment/type",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfTraAdvertismentTypePT> updateAdvertismentTypeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "advertismentTypePT", required = true) @RequestBody ConfTraAdvertismentTypePT advertismentTypePT);


    @ApiOperation(value = "createTax", notes = "", response = ConfTraAdvertismentTypePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfTraAdvertismentTypePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfTraAdvertismentTypePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/advertisment/type",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfTraAdvertismentTypePT> createAdvertismentTypeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "advertismentTypePT", required = true) @RequestBody ConfTraAdvertismentTypePT advertismentTypePT);


    @ApiOperation(value = "deleteTax", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/advertisment/type/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAdvertismentTypeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}