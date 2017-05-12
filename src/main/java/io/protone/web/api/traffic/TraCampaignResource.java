package io.protone.web.api.traffic;

import io.protone.web.rest.dto.traffic.TraCampaignDTO;
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
public interface TraCampaignResource {


    @ApiOperation(value = "getAllCampaigns", notes = "", response = TraCampaignDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraCampaignDTO>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateCampaign", notes = "", response = TraCampaignDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraCampaignDTO> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException;

    @ApiOperation(value = "createCampaign", notes = "", response = TraCampaignDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraCampaignDTO> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteCampaign", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getCampaign", notes = "", response = TraCampaignDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraCampaignDTO> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "getAllCustomerCampaigns", notes = "", response = TraCampaignDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/campaign/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraCampaignDTO>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable);


}
