package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraCustomerOrdersPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkTrafficCustomerInvoice {


    @ApiOperation(value = "getAllInvoicesForCustomer", notes = "", response = TraCustomerOrdersPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerOrdersPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCustomerOrdersPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraInvoicePT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                           @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "notifyAboutUnpaidInvoiceCustomer", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/customer/{customerShortcut}/invoice/{id}/notify",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
