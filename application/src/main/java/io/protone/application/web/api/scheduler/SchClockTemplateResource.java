package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchClockTemplateThinDTO;
import io.protone.scheduler.api.dto.thin.SchEventTemplateThinDTO;
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

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchClockTemplateResource {

    @ApiOperation(value = "createSchedulerClockForChannel", notes = "", response = SchClockTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchClockTemplateDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockTemplateDTO clockDTO) throws URISyntaxException;

    @ApiOperation(value = "getAllSchedulerClockForChannel", notes = "", response = SchEventTemplateThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockTemplateThinDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchClockTemplateThinDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getAllSchedulerClockGroupedByCategoryForChannel", notes = "", response = SchEventTemplateThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockTemplateThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockTemplateThinDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/category/{name}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchClockTemplateThinDTO>> getAllSchedulerClockForChannelGroupedByCategoryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                          @ApiParam(value = "name", required = true) @PathVariable("name") String name,
                                                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getSchedulerClockForChannel", notes = "", response = SchClockTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchClockTemplateDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );


    @ApiOperation(value = "updateSchedulerClockForChannel", notes = "", response = SchClockTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchClockTemplateDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockTemplateDTO clockDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteSchedulerClockForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );

}
