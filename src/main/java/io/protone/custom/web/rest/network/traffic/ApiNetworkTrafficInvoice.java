package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraInvoicePT;
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
public interface ApiNetworkTrafficInvoice {


    @ApiOperation(value = "getAllInvoices", notes = "", response = TraInvoicePT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable);


    @ApiOperation(value = "updateInvoice", notes = "", response = TraInvoicePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 201, message = "Created", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO);


    @ApiOperation(value = "createInvoice", notes = "", response = TraInvoicePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 201, message = "Created", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/invoice",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO);


    @ApiOperation(value = "deleteInvoice", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getInvoice", notes = "", response = TraInvoicePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraInvoicePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraInvoicePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraInvoicePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraInvoicePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "notifyAboutUnpaidInvoice", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/invoice/{id}/notify",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
