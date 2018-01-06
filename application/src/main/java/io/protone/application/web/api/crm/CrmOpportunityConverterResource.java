package io.protone.application.web.api.crm;

import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.api.dto.thin.CrmOpportunityThinDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;

/**
 * Created by lukaszozimek on 30/07/2017.
 */
public interface CrmOpportunityConverterResource {
    @ApiOperation(value = "convertContactToOpportunity", notes = "", response = CrmOpportunityThinDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 204, message = "No Content", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/crm/contact/{shortName}/convert/opportunity",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityDTO> convertContactToOpportunityPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException;

    @ApiOperation(value = "convertLeadToOpportunity", notes = "", response = Void.class, tags = {"CRM"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 204, message = "No Content", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/crm/lead/{shortName}/convert/opportunity",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityDTO> convertLeadToOpportunity(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException;

    @ApiOperation(value = "convertCustomerToOpportunity", notes = "", response = CrmOpportunityThinDTO.class, tags = {"CRM"})

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 204, message = "No Content", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/crm/customer/{shortName}/convert/opportunity",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmOpportunityDTO> convertCustomerToOpportunityPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException;


}
