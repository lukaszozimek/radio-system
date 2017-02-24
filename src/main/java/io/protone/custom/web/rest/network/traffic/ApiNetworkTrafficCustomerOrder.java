package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraOrderPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkTrafficCustomerOrder {


    @ApiOperation(value = "getAllCustomerOrders", notes = "", response = TraOrderPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/customer/{customerShortcut}/order",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderPT>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


}