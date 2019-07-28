package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraMediaPlanTemplateResource {

    @ApiOperation(value = "getAllTraMediaPlanTemplate", notes = "", response = TraMediaPlanTemplateDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic//mediaplan/template",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraMediaPlanTemplateDTO>> getAllTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getTraMediaPlanTemplate", notes = "", response = TraMediaPlanTemplateDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic//mediaplan/template/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraMediaPlanTemplateDTO> getTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTraMediaPlanTemplate", notes = "", response = TraMediaPlanTemplateDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/mediaplan/template",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraMediaPlanTemplateDTO> updateTraMediaPlanTemplateUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException;


    @ApiOperation(value = "createTraMediaPlanTemplate", notes = "", response = TraMediaPlanTemplateDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanTemplateDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic//mediaplan/template",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraMediaPlanTemplateDTO> createTraMediaPlanTemplateUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteTraMediaPlanTemplate", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic//mediaplan/template/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTraMediaPlanTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
