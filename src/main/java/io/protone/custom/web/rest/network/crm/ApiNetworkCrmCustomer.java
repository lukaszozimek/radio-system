package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmAccountPT;
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
public interface ApiNetworkCrmCustomer {


    @ApiOperation(value = "updateCustomer", notes = "", response = CrmAccountPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) throws URISyntaxException;


    @ApiOperation(value = "createCustomer", notes = "", response = CrmAccountPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) throws URISyntaxException;


    @ApiOperation(value = "getAllCustomers", notes = "", response = CrmAccountPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true)  Pageable pagable);


    @ApiOperation(value = "getCustomer", notes = "", response = CrmAccountPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteCustomer", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
