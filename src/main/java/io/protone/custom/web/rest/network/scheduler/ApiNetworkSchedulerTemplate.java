package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.dto.SchEventPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkSchedulerTemplate {


    /* DISABLED !!!

    @ApiOperation(value = "updateSchedulerTemplates", notes = "", response = SchEventPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchEventPT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerTemplates", notes = "", response = SchEventPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchEventPT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT schdeulerTemplate);

    @ApiOperation(value = "getSchedulerTemplate", notes = "", response = SchEventPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchEventPT>> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);



    @ApiOperation(value = "deleteSchedulerTemplate", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates/{shortName}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    */

    @ApiOperation(value = "getAllSchedulerTemplates", notes = "", response = SchEventPT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/templates",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchEventPT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);



}
