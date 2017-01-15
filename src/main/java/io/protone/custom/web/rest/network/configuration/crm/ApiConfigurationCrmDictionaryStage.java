package io.protone.custom.web.rest.network.configuration.crm;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.custom.service.dto.ConfCurrencyPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationCrmDictionaryStage {
    @ApiOperation(value = "getAllCrmStages", notes = "", response = ConfCrmStagePT.class, responseContainer = "List", tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ConfCrmStagePT>> getAllCrmStagesUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConfCrmStagePT> getCrmStageUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);


    @ApiOperation(value = "updateCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<ConfCrmStagePT> updateCrmStageUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "crmStage" ,required=true ) @RequestBody ConfCrmStagePT crmStage);


    @ApiOperation(value = "createCrmStage", notes = "", response = ConfCrmStagePT.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfCrmStagePT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfCrmStagePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfCrmStagePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfCrmStagePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfCrmStagePT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ConfCrmStagePT> createCrmStageUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "crmStage" ,required=true ) @RequestBody ConfCrmStagePT crmStage);


    @ApiOperation(value = "deleteCrmStage", notes = "", response = Void.class, tags={ "DICTIONARY","CONFIGURATION", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/crm/dictionary/stage/{id}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCrmStageUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id",required=true ) @PathVariable("id") Long id);



}
