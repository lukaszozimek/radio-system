package io.protone.custom.web.rest.network.configuration.crm;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.custom.service.dto.ConfLeadStatusPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationCrmDictionaryLeadStatus {


    @ApiOperation(value = "deleteLeadstatus", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadStatusUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllLeadStatus", notes = "", response = ConfLeadStatusPT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfLeadStatusPT>> getAllLeadStatusUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfLeadStatusPT> getLeadStatusUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "updateLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfLeadStatusPT> updateleadStatusUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadStatusPT leadStatus);

    @ApiOperation(value = "createLeadStatus", notes = "", response = ConfLeadStatusPT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfLeadStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfLeadStatusPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/leadstatus/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfLeadStatusPT> createLeadStatusUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "leadStatus" ,required=true ) @RequestBody ConfLeadStatusPT leadStatus);

}
