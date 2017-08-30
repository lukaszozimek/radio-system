package io.protone.application.web.api.scheduler;

import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchEventConfigurationThinDTO;
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
public interface SchEventConfigurationResource {

    @ApiOperation(value = "getAllSchedulerEventForChannel", notes = "", response = SchEventConfigurationThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventConfigurationThinDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventConfigurationThinDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventConfigurationThinDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventConfigurationThinDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchEventConfigurationThinDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchEventConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchEventConfigurationDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventConfigurationDTO schEventDTO) throws URISyntaxException;



    @ApiOperation(value = "deleteSchedulerEventForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "getSchedulerEventForChannel", notes = "", response = SchEventConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchEventConfigurationDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );

    @ApiOperation(value = "updateSchedulerEventForChannel", notes = "", response = SchEventConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchEventConfigurationDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventConfigurationDTO schEventDTO) throws URISyntaxException;

}
