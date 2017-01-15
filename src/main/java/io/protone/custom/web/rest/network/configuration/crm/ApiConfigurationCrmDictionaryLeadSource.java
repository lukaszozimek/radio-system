package io.protone.custom.web.rest.network.configuration.crm;

import io.protone.custom.service.dto.ConfLeadSourcePT;
import io.protone.custom.service.dto.ConfLeadStatusPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "custom", description = "the api API")
public interface ApiConfigurationCrmDictionaryLeadSource {


    @ApiOperation(value = "createLeadsource", notes = "", response = ConfLeadSourcePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfLeadSourcePT> createLeadsourceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadStatus);

    @ApiOperation(value = "deleteLeadsource", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadsourceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllLeadSource", notes = "", response = ConfLeadSourcePT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfLeadSourcePT>> getAllLeadsourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getLeadStatus", notes = "", response = ConfLeadSourcePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/{id}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfLeadSourcePT> getLeadSourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "updateLeadSource", notes = "", response = ConfLeadSourcePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadSourcePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadSourcePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadsource/",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfLeadSourcePT> updateLeadSourceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadStatus);


}
