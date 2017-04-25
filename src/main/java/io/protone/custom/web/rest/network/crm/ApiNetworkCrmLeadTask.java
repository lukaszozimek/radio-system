package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmTaskPT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkCrmLeadTask {


    @ApiOperation(value = "getAllLeadActivities", notes = "", response = CrmTaskPT.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskPT>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "pagable", required = true)  Pageable pagable);

    @ApiOperation(value = "updateLeadActivity", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskPT> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                         @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT);


    @ApiOperation(value = "createLeadActivity", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskPT> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT);


    @ApiOperation(value = "deleteLeadActivity", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getLead", notes = "", response = CrmTaskPT.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskPT> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
