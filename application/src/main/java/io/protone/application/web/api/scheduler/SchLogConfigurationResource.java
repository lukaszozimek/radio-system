package io.protone.application.web.api.scheduler;


import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchLogConfigurationThinDTO;
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
public interface SchLogConfigurationResource {

    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = SchLogConfigurationThinDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogConfigurationThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogConfigurationThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogConfigurationThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogConfigurationThinDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchLogConfigurationThinDTO>> getAllLogsConfigurationsForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = SchLogConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchLogConfigurationDTO> creatLogsConfigurationsForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchLogConfigurationDTO schScheduleDTO) throws URISyntaxException, CreateBucketException;

    @ApiOperation(value = "deleteSchedulerPlaylistForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLogsConfigurationsForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getSchedulerPlaylistForChannel", notes = "", response = SchLogConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchLogConfigurationDTO> getLogsConfigurationsForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = SchLogConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchLogConfigurationDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchLogConfigurationDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/log/configuration",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchLogConfigurationDTO> updateLogsConfigurationsForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchLogConfigurationDTO schScheduleDTO) throws URISyntaxException, CreateBucketException;


}
