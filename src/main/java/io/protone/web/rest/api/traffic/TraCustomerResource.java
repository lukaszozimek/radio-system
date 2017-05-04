package io.protone.web.rest.api.traffic;

import io.protone.web.rest.dto.traffic.TraCustomerDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraCustomerResource {


    @ApiOperation(value = "updateTrafficCustomerUsingPUT", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraCustomerDTO> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestBody TraCustomerDTO traCustomerDTO) throws URISyntaxException;


    @ApiOperation(value = "createTrafficCustomerUsingPOST", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraCustomerDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraCustomerDTO> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestBody TraCustomerDTO traCustomerDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllTrafficCustomersUsingGET", notes = "", response = TraCustomerDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerDTO>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getTrafficCustomerUsingGET", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraCustomerDTO> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
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
