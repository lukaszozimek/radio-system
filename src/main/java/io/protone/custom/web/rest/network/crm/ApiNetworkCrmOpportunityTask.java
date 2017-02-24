package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmTaskPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkCrmOpportunityTask {

    @ApiOperation(value = "getAllOpportunityActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "updateOpportunityActivity", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT);


    @ApiOperation(value = "createOpportunityActivity", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT);

    @ApiOperation(value = "deleteOpportunityActivity", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getOpportunityActivity", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}