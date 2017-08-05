package io.protone.application.web.api.crm;

import io.protone.crm.api.dto.CrmContactDTO;
import io.protone.crm.api.dto.CrmOpportunityDTO;
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
public interface CrmContactConverterResource {
    @ApiOperation(value = "convertLeadToOpportunity", notes = "", response = Void.class, tags = {"CRM"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 204, message = "No Content", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmOpportunityDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmOpportunityDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/lead/{shortName}/convert/contact",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmContactDTO> convertLeadToOpportunity(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException;


}
