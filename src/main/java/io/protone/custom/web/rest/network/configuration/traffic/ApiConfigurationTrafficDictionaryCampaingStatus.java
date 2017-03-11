package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfInvoiceStatusPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationTrafficDictionaryCampaingStatus {

    @ApiOperation(value = "getAllCampaingStatus", notes = "", response = ConfCampaingStatusPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCampaingStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/campaing/status",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCampaingStatusPT>> getAllCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getCampaingStatus", notes = "", response = ConfCampaingStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OKv", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCampaingStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/campaing/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfCampaingStatusPT> getCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateCampaingStatus", notes = "", response = ConfCampaingStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCampaingStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/campaing/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfCampaingStatusPT> updateCampaingStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT);


    @ApiOperation(value = "createCampaingStatus", notes = "", response = ConfCampaingStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCampaingStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCampaingStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/campaing/status",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfCampaingStatusPT> createCampaingStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT);


    @ApiOperation(value = "deleteCampaingStatus", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/campaing/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCampaingStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
