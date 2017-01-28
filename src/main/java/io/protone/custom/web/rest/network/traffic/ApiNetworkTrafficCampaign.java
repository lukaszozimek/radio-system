package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkTrafficCampaign {


    @ApiOperation(value = "getAllCampaigns", notes = "", response = TraCampaignPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "updateCampaign", notes = "", response = TraCampaignPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO);

    @ApiOperation(value = "createCampaign", notes = "", response = TraCampaignPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO);


    @ApiOperation(value = "deleteCampaign", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "getCampaign", notes = "", response = TraCampaignPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraCampaignPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraCampaignPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraCampaignPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraCampaignPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/campaign/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
