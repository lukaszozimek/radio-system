package io.protone.application.web.api.scheduler;

import io.protone.scheduler.api.dto.SchEventTemplateDTO;
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
public interface SchEventTemplateResource {

    @ApiOperation(value = "getAllSchedulerEventForChannel", notes = "", response = SchEventTemplateThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchEventTemplateThinDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchEventTemplateThinDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getAllSchedulerEventGroupedByCategoryForChannel", notes = "", response = SchEventTemplateThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchEventTemplateThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchEventTemplateThinDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/category/{name}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchEventTemplateThinDTO>> getAllSchedulerEventGroupedByCategoryForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                          @ApiParam(value = "name", required = true) @PathVariable("name") String name,
                                                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchEventTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchEventTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchEventTemplateDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventTemplateDTO schEventDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteSchedulerEventForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "getSchedulerEventForChannel", notes = "", response = SchEventTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchEventTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchEventTemplateDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );

    @ApiOperation(value = "updateSchedulerEventForChannel", notes = "", response = SchEventTemplateDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchEventTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchEventTemplateDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchEventTemplateDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventTemplateDTO schEventDTO) throws URISyntaxException;

}
