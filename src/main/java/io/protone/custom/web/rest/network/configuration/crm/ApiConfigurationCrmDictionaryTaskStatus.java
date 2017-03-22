package io.protone.custom.web.rest.network.configuration.crm;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationCrmDictionaryTaskStatus {
    @ApiOperation(value = "getAllCrmTaskStatus", notes = "", response = ConfCrmTaskStatusPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmTaskStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/task/status",
        produces = {"application/json"},

        method = RequestMethod.GET)
    ResponseEntity<List<ConfCrmTaskStatusPT>> getAllCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getCrmTaskStatus", notes = "", response = ConfCrmTaskStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmTaskStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/task/status/{id}",
        produces = {"application/json"},

        method = RequestMethod.GET)
    ResponseEntity<ConfCrmTaskStatusPT> getCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTaskStatus", notes = "", response = ConfCrmTaskStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmTaskStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/task/status/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfCrmTaskStatusPT> updateCrmTaskStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT crmStage);


    @ApiOperation(value = "createTaskStatus", notes = "", response = ConfCrmTaskStatusPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmTaskStatusPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmTaskStatusPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/task/status/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfCrmTaskStatusPT> createCrmTaskStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT crmStage);


    @ApiOperation(value = "deleteCrmTaskStatus", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/task/status/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCrmTaskStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
