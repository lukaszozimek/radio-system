package io.protone.application.web.api.crm;


import io.protone.crm.api.dto.CrmDiscountDTO;
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
public interface CrmDiscountResource {

    @ApiOperation(value = "getAllDiscount", notes = "", response = CrmDiscountDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/crm/discount",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmDiscountDTO>> getAllDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getDiscount", notes = "", response = CrmDiscountDTO.class, tags = {"CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/crm/discount/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmDiscountDTO> getDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateDiscount", notes = "", response = CrmDiscountDTO.class, tags = {"CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmDiscountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/crm/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmDiscountDTO> updateDiscountUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "discountPT", required = true) @RequestBody CrmDiscountDTO discountPT) throws URISyntaxException;


    @ApiOperation(value = "createDiscount", notes = "", response = CrmDiscountDTO.class, tags = {"CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmDiscountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmDiscountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmDiscountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmDiscountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmDiscountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/crm/discount",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmDiscountDTO> createDiscountUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "discountPT", required = true) @RequestBody CrmDiscountDTO discountPT) throws URISyntaxException;


    @ApiOperation(value = "deleteDiscount", notes = "", response = Void.class, tags = {"CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/crm/discount/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDiscountUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
