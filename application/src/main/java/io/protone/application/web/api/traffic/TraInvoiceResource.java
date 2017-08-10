package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraInvoiceDTO;
import io.protone.traffic.api.dto.thin.TraInvoiceThinDTO;
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
public interface TraInvoiceResource {


    @ApiOperation(value = "getAllInvoices", notes = "", response = TraInvoiceDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraInvoiceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoiceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraInvoiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraInvoiceDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TraInvoiceThinDTO>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateInvoice", notes = "", response = TraInvoiceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraInvoiceDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraInvoiceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoiceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraInvoiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraInvoiceDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<TraInvoiceDTO> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "traInvoiceDTO", required = true) @Valid @RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException;


    @ApiOperation(value = "createInvoice", notes = "", response = TraInvoiceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraInvoiceDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraInvoiceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoiceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraInvoiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraInvoiceDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraInvoiceDTO> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "traInvoiceDTO", required = true) @Valid @RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteInvoice", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getInvoice", notes = "", response = TraInvoiceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraInvoiceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoiceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraInvoiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraInvoiceDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TraInvoiceDTO> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyAboutUnpaidInvoice", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice/{id}/notify",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllInvoicesForCustomer", notes = "", response = TraInvoiceDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraInvoiceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoiceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraInvoiceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraInvoiceDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice/customer/{customerShortcut}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TraInvoiceDTO>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable);

}
