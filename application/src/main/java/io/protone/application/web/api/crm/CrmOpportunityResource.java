package io.protone.application.web.api.crm;


import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.api.dto.thin.CrmOpportunityThinDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CrmOpportunityResource {


    @ApiOperation(value = "updateOpportunity", notes = "", response = CrmOpportunityDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmOpportunityDTO> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "crmOpportunityDTO", required = true) @Valid @RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException;


    @ApiOperation(value = "createOpportunity", notes = "", response = CrmOpportunityDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityDTO> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "crmOpportunityDTO", required = true) @Valid @RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllOpportunities", notes = "", response = CrmOpportunityDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmOpportunityThinDTO>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getOpportunity", notes = "", response = CrmOpportunityDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmOpportunityDTO> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteOpportunity", notes = "", response = Void.class, tags = {"CRM"})
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
