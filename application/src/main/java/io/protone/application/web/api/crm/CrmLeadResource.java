package io.protone.application.web.api.crm;

import io.protone.web.rest.dto.crm.CrmLeadDTO;
import io.protone.web.rest.dto.crm.thin.CrmLeadThinDTO;
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
public interface CrmLeadResource {


    @ApiOperation(value = "updateLead", notes = "", response = CrmLeadDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmLeadDTO> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "crmLeadDTO", required = true) @RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException;

    @ApiOperation(value = "createLead", notes = "", response = CrmLeadDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmLeadDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmLeadDTO> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "crmLeadDTO", required = true) @RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException;

    @ApiOperation(value = "getAllLeads", notes = "", response = CrmLeadDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmLeadThinDTO>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getLead", notes = "", response = CrmLeadDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmLeadDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmLeadDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmLeadDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmLeadDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmLeadDTO> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteLead", notes = "", response = Void.class, tags = {"CRM"})
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
