package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraOrderDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraOrderResource {


    @ApiOperation(value = "updateAnOrder", notes = "", response = TraOrderDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraOrderDTO> updateAnOrderUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                      @ApiParam(value = "traOrderDTO", required = true) @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException;

    @ApiOperation(value = "createAnOrder", notes = "", response = TraOrderDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraOrderDTO> createAnOrderUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                       @ApiParam(value = "traOrderDTO", required = true) @RequestBody TraOrderDTO traOrderDTO) throws URISyntaxException;

    @ApiOperation(value = "getAllAnOrders", notes = "", response = TraOrderDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderThinDTO>> getAllAnOrdersUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getAnOrder", notes = "", response = TraOrderDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraOrderDTO> getAnOrderUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "deleteAnOrder", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyCustomerAboutUnpaidOrder", notes = "", response = TraOrderDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraOrderDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order/{id}/notify",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraOrderDTO> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllCustomerOrders", notes = "", response = TraOrderThinDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraOrderThinDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraOrderThinDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraOrderThinDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraOrderThinDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/order/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraOrderThinDTO>> getAllCustomerOrdersUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


}
