package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmContactPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkCrmContact {

    @ApiOperation(value = "updateContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT);


    @ApiOperation(value = "createContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT);


    @ApiOperation(value = "getAllContact", notes = "", response = CrmContactPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getContact", notes = "", response = CrmContactPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}",
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
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
