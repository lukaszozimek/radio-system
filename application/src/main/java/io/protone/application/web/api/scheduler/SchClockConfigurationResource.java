package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchClockConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchClockConfigurationThinDTO;
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
public interface SchClockConfigurationResource {

    @ApiOperation(value = "createSchedulerClockForChannel", notes = "", response = SchClockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchClockConfigurationDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockConfigurationDTO clockDTO) throws URISyntaxException;

    @ApiOperation(value = "getAllSchedulerClockForChannel", notes = "", response = SchEventConfigurationThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockConfigurationThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockConfigurationThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockConfigurationThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockConfigurationThinDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchClockConfigurationThinDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getSchedulerClockForChannel", notes = "", response = SchClockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchClockConfigurationDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );


    @ApiOperation(value = "updateSchedulerClockForChannel", notes = "", response = SchClockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchClockConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchClockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchClockConfigurationDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockConfigurationDTO clockDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteSchedulerClockForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );

}
