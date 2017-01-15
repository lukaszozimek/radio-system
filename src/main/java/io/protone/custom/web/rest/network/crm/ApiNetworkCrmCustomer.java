package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmLeadPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkCrmCustomer {


    @ApiOperation(value = "updateCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmAccountPT customeryPT);


    @ApiOperation(value = "createCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "customerPT" ,required=true ) @RequestBody CrmAccountPT customerPT);


    @ApiOperation(value = "getAllCustomers", notes = "", response = CrmAccountPT.class, responseContainer = "List", tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getCustomer", notes = "", response = CrmAccountPT.class, tags={ "TRAFFIC","CRM", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteCustomer", notes = "", response = Void.class, tags={ "TRAFFIC", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/customer/{shortName}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "shortName",required=true ) @PathVariable("shortName") String shortName);



}
