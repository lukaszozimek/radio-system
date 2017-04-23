package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkCrmOpportunity {


    @ApiOperation(value = "updateOpportunity", notes = "", response = CrmOpportunityPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmOpportunityPT> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT);


    @ApiOperation(value = "createOpportunity", notes = "", response = CrmOpportunityPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityPT> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT);


    @ApiOperation(value = "getAllOpportunities", notes = "", response = CrmOpportunityPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmOpportunityPT>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getOpportunity", notes = "", response = CrmOpportunityPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmOpportunityPT> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteOpportunity", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
