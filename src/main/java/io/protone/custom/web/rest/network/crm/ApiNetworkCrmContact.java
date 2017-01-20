package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkCrmContact {

    @ApiOperation(value = "updateContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customeryPT);


    @ApiOperation(value = "createContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customerPT);


    @ApiOperation(value = "getAllContact", notes = "", response = CrmContactPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteContact", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/contact/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
