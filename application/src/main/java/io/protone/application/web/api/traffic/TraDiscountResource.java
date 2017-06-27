package io.protone.application.web.api.traffic;

import io.protone.web.rest.dto.cor.CorTaxDTO;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
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
public interface TraDiscountResource {

    @ApiOperation(value = "getAllDiscount", notes = "", response = TraDiscountDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraDiscountDTO>> getAllDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getDiscount", notes = "", response = TraDiscountDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraDiscountDTO> getDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateDiscount", notes = "", response = TraDiscountDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraDiscountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraDiscountDTO> updateDiscountUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "discountPT", required = true) @RequestBody TraDiscountDTO discountPT) throws URISyntaxException;


    @ApiOperation(value = "createDiscount", notes = "", response = CorTaxDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraDiscountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraDiscountDTO> createDiscountUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "discountPT", required = true) @RequestBody TraDiscountDTO discountPT) throws URISyntaxException;


    @ApiOperation(value = "deleteDiscount", notes = "", response = Void.class, tags = {"TRAFFIC",})
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
