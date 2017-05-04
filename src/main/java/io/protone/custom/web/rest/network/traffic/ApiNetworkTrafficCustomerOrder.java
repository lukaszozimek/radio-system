package io.protone.custom.web.rest.network.traffic;

import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkTrafficCustomerOrder {


    @ApiOperation(value = "getAllCustomerOrders", notes = "", response = TraOrderDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}/order",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderDTO>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                   @ApiParam(value = "pagable", required = true)  Pageable pagable);


}
