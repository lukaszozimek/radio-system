package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.SchTemplatePT;
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
public interface ApiNetworkTrafficTemplate {


    @ApiOperation(value = "updateTrafficTemplates", notes = "", response = SchTemplatePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchTemplatePT> updateTrafficTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "createTrafficTemplates", notes = "", response = SchTemplatePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 201, message = "Created", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchTemplatePT> creatTrafficTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate);


    @ApiOperation(value = "getAllTrafficTemplates", notes = "", response = SchTemplatePT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getTrafficTemplate", notes = "", response = SchTemplatePT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchTemplatePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchTemplatePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchTemplatePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchTemplatePT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchTemplatePT> getTrafficTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteTrafficTemplate", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/traffic/templates/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
