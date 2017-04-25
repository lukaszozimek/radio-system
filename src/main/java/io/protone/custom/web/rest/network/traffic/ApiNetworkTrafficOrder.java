package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraOrderPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkTrafficOrder {


    @ApiOperation(value = "updateAnOrder", notes = "", response = TraOrderPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT);

    @ApiOperation(value = "createAnOrder", notes = "", response = TraOrderPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "traOrderPT", required = true) @RequestBody TraOrderPT traOrderPT);

    @ApiOperation(value = "getAllAnOrders", notes = "", response = TraOrderPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "getAnOrder", notes = "", response = TraOrderPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteAnOrder", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyCustomerAboutUnpaidOrder", notes = "", response = TraOrderPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/order/{id}/notify",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
