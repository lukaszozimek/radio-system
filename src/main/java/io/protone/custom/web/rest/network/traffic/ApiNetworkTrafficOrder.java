package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkTrafficOrder {


    @ApiOperation(value = "updateAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "anOrderDTO" ,required=true ) @RequestBody TraOrderPT anOrderDTO);

    @ApiOperation(value = "createAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "anOrderDTO" ,required=true ) @RequestBody TraOrderPT anOrderDTO);

    @ApiOperation(value = "getAllAnOrders", notes = "", response = TraOrderPT.class, responseContainer = "List", tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getAnOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteAnOrder", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);



    @ApiOperation(value = "notifyCustomerAboutUnpaidOrder", notes = "", response = TraOrderPT.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/order/{id}/notify",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);



}
