package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmLeadPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkCrmLead {


    @ApiOperation(value = "updateLead", notes = "", response = CrmLeadPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                 @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT);

    @ApiOperation(value = "createLead", notes = "", response = CrmLeadPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT);

    @ApiOperation(value = "getAllLeads", notes = "", response = CrmLeadPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getLead", notes = "", response = CrmLeadPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteLead", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
