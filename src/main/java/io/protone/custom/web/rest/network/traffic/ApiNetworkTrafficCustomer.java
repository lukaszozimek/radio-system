package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraCustomerPT;
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
public interface ApiNetworkTrafficCustomer {


    @ApiOperation(value = "updateTrafficCustomerUsingPUT", notes = "", response = TraCustomerPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "CustomerVM", required = true) @RequestBody TraCustomerPT customer);


    @ApiOperation(value = "createTrafficCustomerUsingPOST", notes = "", response = TraCustomerPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "customerVM", required = true) @RequestBody TraCustomerPT customer);


    @ApiOperation(value = "getAllTrafficCustomersUsingGET", notes = "", response = TraCustomerPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "getTrafficCustomerUsingGET", notes = "", response = TraCustomerPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "deleteTrafficCustomerUsingDELETE", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


}
